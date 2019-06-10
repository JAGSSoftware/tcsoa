/*
 * (c) 2013 - Jose A. Garcia Sanchez
 */
package org.jag.teamcenter.soa.types;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.teamcenter.soa.SoaConstants;

/**
 * @author Jose A. Garcia Sanchez
 */
public enum Protocol {
    HTTP(new String[]{"http", "https"}, SoaConstants.HTTP),
    IIOP(new String[]{"iiop", "ior"}, SoaConstants.IIOP),
    TCCS(new String[]{"tccs"}, SoaConstants.TCCS);

    private static final Map<String, Protocol> map = new TreeMap<>();
    private final String[] protocols;
    private final String value;

    static {
        for (final Protocol enumerationProtocol : values()) {
            for (final String protocol : enumerationProtocol.protocols) {
                map.put(protocol, enumerationProtocol);
            }
        }
    }

    Protocol(final String[] protocols, final String value) {
        this.protocols = protocols;
        this.value = value;
    }

    /**
     * @return
     */
    public String value() {
        return value;
    }

    /**
     * @param hostPath
     * @return
     */
    public static Protocol getProtocolFromHostpath(final String hostPath) {
        if (hostPath == null) {
            throw new IllegalArgumentException("hostPath is null");
        }

        final Matcher matcher = Pattern.compile("(\\w*):.*").matcher(hostPath);
        final Protocol protocol;

        if (matcher.matches()) {
            protocol = map.get(matcher.group(1).toLowerCase());
        } else {
            protocol = null;
        }

        return protocol;
    }
}
