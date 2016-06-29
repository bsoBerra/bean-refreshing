package com.stanislav.berdnik;

import com.stanislav.berdnik.bean.ChangeableBean;
import com.stanislav.berdnik.bean.UnchangeableBean;
import org.springframework.beans.factory.annotation.Autowired;

public class ClassForAutowiring {

    @Autowired
    ChangeableBean changeableBean;

    @Autowired
    UnchangeableBean unchangeableBean;

}
