/*
 * (c) 2013 - Jose A. Garcia Sanchez
 */
package org.jag.teamcenter.soa.types;

/**
 * @author Jose A. Garcia Sanchez
 */
public enum SoaEPMAction {
    ASSIGN("SOA_EPM_assign_action"),
    START("SOA_EPM_start_action"),
    COMPLETE("SOA_EPM_complete_action"),
    SKIP("SOA_EPM_skip_action"),
    SUSPEND("SOA_EPM_suspend_action"),
    RESUME("SOA_EPM_resume_action"),
    UNDO("SOA_EPM_undo_action"),
    ABORT("SOA_EPM_abort_action"),
    PERFORM("SOA_EPM_perform_action"),
    ADD_ATTACHMENT("SOA_EPM_add_attachment_action"),
    REMOVE_ATTACHMENT("SOA_EPM_remove_attachment_action"),
    APPROVE("SOA_EPM_approve_action"),
    REJECT("SOA_EPM_reject_action"),
    PROMOTE("SOA_EPM_promote_action"),
    DEMOTE("SOA_EPM_demote_action"),
    REFUSE("SOA_EPM_refuse_action"),
    ASSIGN_APPROVER("SOA_EPM_assign_approver_action"),
    NOTIFY("SOA_EPM_notify_action"),
    NO("SOA_EPM_no_action");

    private final String value;

    /**
     * @param value
     */
    private SoaEPMAction(final String value) {
        this.value = value;
    }

    /**
     * @return
     */
    public final String getValue() {
        return value;
    }
}
