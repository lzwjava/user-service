package com.demo.lab.user_role;

import com.demo.lab.base.BaseResponse;

public class UserRoleCheckResponse extends BaseResponse {

    private boolean check;

    public UserRoleCheckResponse(boolean check) {
        this.check = check;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

}
