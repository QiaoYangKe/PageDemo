package com.example.pagedemo.models.sessions;

public class GetCurrentLoginInformationsOutput {

    private ApplicationInfoDto application;

    private UserLoginInfoDto user;

    private TenantLoginInfoDto tenant;

    public ApplicationInfoDto getApplication() {
        return application;
    }

    public void setApplication(ApplicationInfoDto application) {
        this.application = application;
    }

    public UserLoginInfoDto getUser() {
        return user;
    }

    public void setUser(UserLoginInfoDto user) {
        this.user = user;
    }

    public TenantLoginInfoDto getTenant() {
        return tenant;
    }

    public void setTenant(TenantLoginInfoDto tenant) {
        this.tenant = tenant;
    }
}
