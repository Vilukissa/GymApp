package com.calicode.gymapp.app.util.componentprovider;

import com.calicode.gymapp.app.model.OperationCreator;
import com.calicode.gymapp.app.model.UserSessionManager;
import com.calicode.gymapp.app.model.authentication.AuthenticationModel;
import com.calicode.gymapp.app.model.login.LoginModel;
import com.calicode.gymapp.app.model.logout.LogoutModel;
import com.calicode.gymapp.app.model.workout.add.AddWorkoutDayModel;
import com.calicode.gymapp.app.model.workout.days.WorkoutDaysModel;
import com.calicode.gymapp.app.model.workout.movename.MoveNameModel;
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
    private Map<Class<?>, Component> mTaskComponents = new HashMap<Class<?>, Component>();

    public static ComponentProvider get() {
        return INSTANCE;
    }

    private ComponentProvider() {
        addComponents();
    }

    private <T extends Component> void addComponent(T instance) {
        mComponents.put(instance.getClass(), instance);
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> T getComponent(Class<T> clazz) {
        return (T) mComponents.get(clazz);
    }

    public void destroyComponents() {
        destroyComponentList(mComponents);
        destroyComponentList(mTaskComponents);
        mTaskComponents = new HashMap<Class<?>, Component>();
    }

    private void destroyComponentList(Map<Class<?>, Component> items) {
        for (Map.Entry<Class<?>, Component> entry : items.entrySet()) {
            Component component = entry.getValue();
            if (SessionComponent.class.isAssignableFrom(component.getClass())) {
                Log.debug("Destroying session component: " + component.getClass().getName());
                ((SessionComponent) component).destroy();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> T createOrGetTaskComponent(Class<T> clazz) {
        Component taskComponent = mTaskComponents.get(clazz);
        if (taskComponent == null) {
            try {
                taskComponent = clazz.newInstance();
                mTaskComponents.put(clazz, taskComponent);
            } catch (Exception ex) {
                Log.error("Could not instantiate class " + clazz.getSimpleName(), ex);
            }
        }
        return (T) taskComponent;
    }

    public <T extends Component> void destroyTaskComponent(Class<T> clazz) {
        mTaskComponents.remove(clazz);
    }

    @Override
    public void addComponents() {
        addComponent(new VolleyHandler());
        addComponent(new OperationCreator());
        addComponent(new Navigator());
        addComponent(new UserSessionManager());

        // Operation models
        addComponent(new AuthenticationModel());
        addComponent(new LoginModel());
        addComponent(new LogoutModel());
        addComponent(new WorkoutDaysModel());
        addComponent(new AddWorkoutDayModel());
        addComponent(new MoveNameModel());
    }
}