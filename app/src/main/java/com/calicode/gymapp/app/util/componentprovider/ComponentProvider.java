package com.calicode.gymapp.app.util.componentprovider;

import com.calicode.gymapp.app.model.OperationCreator;
import com.calicode.gymapp.app.model.authentication.AuthenticationModel;
import com.calicode.gymapp.app.model.login.LoginModel;
import com.calicode.gymapp.app.navigation.Navigator;
import com.calicode.gymapp.app.network.VolleyHandler;
import com.calicode.gymapp.app.util.Log;
import com.calicode.gymapp.app.util.componentprovider.componentinterfaces.Component;
import com.calicode.gymapp.app.util.componentprovider.componentinterfaces.SessionComponent;

import java.util.HashMap;
import java.util.Map;

public final class ComponentProvider implements ComponentInitializer {

    private static final ComponentProvider INSTANCE = new ComponentProvider();

    private Map<Class<?>, Component> mComponents = new HashMap<Class<?>, Component>();

    public static ComponentProvider get() {
        return INSTANCE;
    }

    private ComponentProvider() {
        addComponents();
    }

    private <T extends Component> void addComponent(T instance) {
        mComponents.put(instance.getClass(), instance);
    }

    public <T extends Component> T getComponent(Class<T> clazz) {
        return (T) mComponents.get(clazz);
    }

    public void destroyComponents() {
        for (Map.Entry<Class<?>, Component> entry : mComponents.entrySet()) {
            Component component = entry.getValue();
            if (SessionComponent.class.isAssignableFrom(component.getClass())) {
                Log.debug("Destroying session component: " + component.getClass().getName());
                ((SessionComponent) component).destroy();
            }
        }
    }

    @Override
    public void addComponents() {
        addComponent(new VolleyHandler());
        addComponent(new OperationCreator());
        addComponent(new Navigator());

        // Operation models
        addComponent(new AuthenticationModel());
        addComponent(new LoginModel());
    }
}