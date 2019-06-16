/*
 * (c) 2013 - Jose A. Garcia Sanchez
 */
package org.jag.teamcenter.soa.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;

import com.teamcenter.services.strong.core._2008_06.DataManagement;

/**
 * The class contains the filter fields for the structure
 * {@link com.teamcenter.services.strong.core._2008_06.DataManagement.ItemInfo}.
 * It's an immutable object that creates the later needed filter structure
 * calling the function {@link #itemInfo()}. <br/>
 * Prior to make use of the {@link #itemInfo()} function, an object of the class
 * must be created with the help of the {@link ItemInfo.Builder} builder class. <br/>
 * Examples: <br/>
 * <ol>
 * <li>
 * {@code final ItemInfo itemInfo = new ItemInfo.Builder("client id").id("000030").build();}
 * </li>
 * <li>
 * {@code final ItemInfo itemInfo = new ItemInfo.Builder("client id").uid("QaKAAADooJRX_C").build();}
 * </li>
 * <li>
 * {@code final ItemInfo itemInfo = new ItemInfo.Builder("client id").uid("QaKAAADooJRX_C").id("000030").build();}
 * </li>
 * </ol>
 *
 * @author Jose A. Garcia Sanchez
 * @see com.teamcenter.services.strong.core._2008_06.DataManagement.ItemInfo
 */
@Deprecated
public final class ItemInfo {
	/** Client identifier. It should be the same in all filter structures. */
	private final String clientId;

	/** Collection of item IDs to be filtered. */
	private final DataManagement.AttrInfo[] ids;

	/** UID of the item to be filtered. */
	private final String uid;

	/**
	 * Flag to filter first by ID ({@code useIdFirst = true}), or by UID (
	 * {@code useIdFirst = false}).
	 */
	private final boolean useIdFirst;

	/**
	 * @param builder
	 *            Builder with the filter information to be used as the final
	 *            filter.
	 */
	private ItemInfo(final Builder builder) {
		this.clientId = builder.clientId;
		// this.ids = builder.ids.clone();
		this.ids = builder.ids.toArray(new DataManagement.AttrInfo[builder.ids
				.size()]);
		this.uid = builder.uid;
		this.useIdFirst = builder.useIdFirst;
	}

	/**
	 * Creates the filter structure {@link DataManagement.ItemInfo} to be used
	 * by the corresponding SOA Teamcenter service. The function is
	 * synchronized.
	 *
	 * @return {@link com.teamcenter.services.strong.core._2008_06.DataManagement#ItemInfo}
	 *         structure with the configured filter
	 */
	public synchronized DataManagement.ItemInfo itemInfo() {
		final DataManagement.ItemInfo itemInfo = new DataManagement.ItemInfo();

		itemInfo.clientId = this.clientId;
		itemInfo.ids = this.ids.clone();
		itemInfo.uid = this.uid;
		itemInfo.useIdFirst = this.useIdFirst;

		return itemInfo;
	}

	/**
	 * Builder class to construct an immutable instance of {@link ItemInfo}. The
	 * fields {@code uid} and {@code ids} can be used together in order to find
	 * the corresponding item, just the order is set by the value of
	 * {@code useIdFirst}. For this reason when building the filter object, the
	 * order of the functions {@link #uid(String)} and {@link #id(String)} in
	 * the chain of calls sets appropriately the value of {@link #useIdFirst},
	 * having the value corresponding of the last call. <br/>
	 * For example, when calling:
	 * <ul>
	 * <li>
	 * {@code ItemInfo.Builder itemInfo = new ItemInfo.Builder(clientId).uid("myUID");}
	 * will set {@code useIdFirst = false}</li>
	 * <li>
	 * {@code ItemInfo.Builder itemInfo = new ItemInfo.Builder(clientId).id("myID");}
	 * will set {@code useIdFirst = true}</li>
	 * <li>
	 * {@code ItemInfo.Builder itemInfo = new ItemInfo.Builder(clientId).uid("myUID").id("myID");}
	 * will set {@code useIdFirst = true}</li>
	 * <li>
	 * {@code ItemInfo.Builder itemInfo = new ItemInfo.Builder(clientId).id("myID").uid("myUID");}
	 * will set {@code useIdFirst = false}</li>
	 * </ul>
	 *
	 * @author Jose A. Garcia Sanchez
	 */
	public static class Builder {
		/** Client identifier. */
		private final String clientId;

		/** Flag to use first the <i>ID</i> or the <i>UID</i> as filter. */
		private boolean useIdFirst = false;

		/** <i>UID</i> of the filtered item. */
		private String uid = "";

		/** Structure that will contain the <i>ID</i> of the item. */
		// private DataManagement.AttrInfo[] ids = new
		// DataManagement.AttrInfo[0];
		private List<DataManagement.AttrInfo> ids = new ArrayList<DataManagement.AttrInfo>();

		/**
		 * @param clientId
		 *            SOA client identifier
		 * @throws NullPointerException
		 *             if the parameter is null
		 */
		public Builder(final String clientId) {
			if (clientId == null) {
				throw new NullPointerException("clientId is null");
			}
			this.clientId = clientId;
		}

		/**
		 * Selects the <i>ID</i> as first and only filter to look for an Item.
		 * It empties the <i>UID</i> field and set {@code useIdFirst = true}.
		 *
		 * @param id
		 *            Item identifier
		 * @return Instance of {@link ItemInfo}
		 * @throws NullPointerException
		 *             if the parameter is null
		 */
		public ItemInfo.Builder id(final String id) {
			if (id == null) {
				throw new NullPointerException("id is null");
			}

			this.useIdFirst = true;
			// this.ids = new DataManagement.AttrInfo[] {
			// newAttributeInfoItemId(id) };
			// ids.add(newAttributeInfoItemId(id));
			ids.add(newAttributeInfo("item_id", id));

			return this;
		}

		public ItemInfo.Builder attributeInfo(final String name,
				final String value) {
			Validate.notNull(name);
			Validate.notNull(value);

			ids.add(newAttributeInfo(name, value));

			return this;
		}

		/**
		 * @param name
		 * @param value
		 * @return
		 */
		private DataManagement.AttrInfo newAttributeInfo(final String name,
				final String value) {
			final DataManagement.AttrInfo attribute = new DataManagement.AttrInfo();
			attribute.name = name;
			attribute.value = value;

			return attribute;
		}

		/**
		 * Selects the <i>UID</i> as first and only filter to look for an Item.
		 *
		 * @param uid
		 *            UID of the sought Item
		 * @return Instance of {@link ItemInfo}
		 * @throws NullPointerException
		 *             if the parameter is null
		 */
		public ItemInfo.Builder uid(final String uid) {
			if (uid == null) {
				throw new NullPointerException("uid is null");
			}

			this.useIdFirst = false;
			this.uid = uid;

			return this;
		}

		/**
		 * @param itemId
		 *            <i>ID</i> of the item to be sought
		 * @return Instance of {@link DataManagement.AttrInfo} with the item ID
		 */
		private DataManagement.AttrInfo newAttributeInfoItemId(
				final String itemId) {
			final DataManagement.AttrInfo attribute = new DataManagement.AttrInfo();

			attribute.name = "item_id";
			attribute.value = itemId;

			return attribute;
		}

		/**
		 * @return Instance of {@link ItemInfo} with the values provided to the
		 *         builder
		 */
		public ItemInfo build() {
			return new ItemInfo(this);
		}
	}
}
