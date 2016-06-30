package com.stanislav.berdnik;

import com.stanislav.berdnik.bean.ChangeableBean;
import com.stanislav.berdnik.bean.UnchangeableBean;
import org.springframework.beans.factory.annotation.Autowired;

public class ClassForAutowiring {

    @Autowired
    ChangeableBean changeableBean;

    @Autowired
    UnchangeableBean unchangeableBean;

    public ChangeableBean getChangeableBean() {
        return changeableBean;
    }

    public void setChangeableBean(ChangeableBean changeableBean) {
        this.changeableBean = changeableBean;
    }

    public UnchangeableBean getUnchangeableBean() {
        return unchangeableBean;
    }

    public void setUnchangeableBean(UnchangeableBean unchangeableBean) {
        this.unchangeableBean = unchangeableBean;
    }
}
