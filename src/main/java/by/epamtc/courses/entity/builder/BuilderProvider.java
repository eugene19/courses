package by.epamtc.courses.entity.builder;

public final class BuilderProvider {
    private static final BuilderProvider instance = new BuilderProvider();

    private final UserBuilder userBuilder = new UserBuilder();
    private final CourseBuilder courseBuilder = new CourseBuilder();

    private BuilderProvider() {
    }

    public static BuilderProvider getInstance() {
        return instance;
    }

    public UserBuilder getUserBuilder() {
        return userBuilder;
    }

    public CourseBuilder getCourseBuilder() {
        return courseBuilder;
    }
}
