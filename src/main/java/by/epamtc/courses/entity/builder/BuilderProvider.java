package by.epamtc.courses.entity.builder;

/**
 * Class that provides access to builder objects
 *
 * @author DEA
 */
public final class BuilderProvider {

    /**
     * Provider instance
     */
    private static final BuilderProvider instance = new BuilderProvider();

    /**
     * User builder instance
     */
    private final UserBuilder userBuilder = new UserBuilder();

    /**
     * Course builder instance
     */
    private final CourseBuilder courseBuilder = new CourseBuilder();

    /**
     * Construct a BuilderProvider
     */
    private BuilderProvider() {
    }

    /**
     * @return instance of BuilderProvider
     */
    public static BuilderProvider getInstance() {
        return instance;
    }

    /**
     * @return instance of user builder
     */
    public UserBuilder getUserBuilder() {
        return userBuilder;
    }

    /**
     * @return instance of course builder
     */
    public CourseBuilder getCourseBuilder() {
        return courseBuilder;
    }
}
