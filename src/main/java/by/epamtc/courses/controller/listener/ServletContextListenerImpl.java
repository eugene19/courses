package by.epamtc.courses.controller.listener;

import by.epamtc.courses.dao.impl.connection.ConnectionPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Class for implementing action when servlet context is destroyed
 *
 * @author DEA
 */
public class ServletContextListenerImpl implements ServletContextListener {

    /**
     * Receives notification that the ServletContext is about to be
     * shut down.
     *
     * <p>All servlets and filters will have been destroyed before any
     * ServletContextListeners are notified of context
     * destruction.
     *
     * @param sce the ServletContextEvent containing the ServletContext
     *            that is being destroyed
     * @implSpec The default implementation takes no action.
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().dispose();
    }
}
