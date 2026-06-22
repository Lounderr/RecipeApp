package model;

import java.util.List;

public class Role {
    private RoleEnum roleEnum;
    private List<String> permissions;

    public Role(RoleEnum roleEnum) {
        this.roleEnum = roleEnum;
        if (roleEnum == RoleEnum.ADMIN) {
            this.permissions = List.of(
                "READ_RECIPES", "ADD_FAVORITE", "REMOVE_FAVORITE", "REPORT_RECIPE", 
                "CREATE_RECIPE", "UPDATE_RECIPE", "DELETE_RECIPE", "PROMOTE_USER", "DEMOTE_ADMIN", "VIEW_REPORTS"
            );
        } else {
            this.permissions = List.of(
                "READ_RECIPES", "ADD_FAVORITE", "REMOVE_FAVORITE", "REPORT_RECIPE"
            );
        }
    }

    public RoleEnum getRoleEnum() {
        return roleEnum;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    @Override
    public String toString() {
        return roleEnum.name();
    }
}
