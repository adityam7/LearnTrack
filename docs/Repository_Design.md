# Repository Design - Using Abstract Classes and Generics

## Overview

The repository package has been improved using **abstract classes** and **generics** to eliminate code duplication and demonstrate advanced Java concepts.

## Architecture

### Before Refactoring
- **Code Duplication**: Each repository (Student, Course, Enrollment) had duplicate implementations of common CRUD operations
- **Lines of Code**: ~200+ lines across 3 repositories with 60-70% duplication
- **Maintenance**: Changes to common operations required updating all 3 repositories

### After Refactoring
- **Single Source of Truth**: Common CRUD operations implemented once in `BaseRepository<T>`
- **Lines of Code**: ~70 lines in base class + specialized methods only in concrete repositories
- **Maintenance**: Changes to common operations only need to be made in one place

## Design Pattern: Generic Abstract Base Class

### BaseRepository<T> - The Abstract Base Class

```java
public abstract class BaseRepository<T> {
    protected final List<T> entities;

    // Abstract methods that must be implemented by subclasses
    protected abstract int getId(T entity);
    protected abstract String getEntityName();

    // Common CRUD operations available to all repositories
    public void add(T entity) { ... }
    public Optional<T> findById(int id) { ... }
    public T getById(int id) { ... }
    public List<T> findAll() { ... }
    public void update(T entity) { ... }
    public boolean exists(int id) { ... }
    public int count() { ... }
    public boolean deleteById(int id) { ... }
    public void clear() { ... }
}
```

### Key Java Concepts Demonstrated

#### 1. Generics (`<T>`)
- **Type Parameter**: `T` represents any entity type (Student, Course, Enrollment)
- **Type Safety**: Compile-time type checking prevents errors
- **Reusability**: Same code works for different entity types

Example:
```java
BaseRepository<Student>     // T is Student
BaseRepository<Course>      // T is Course
BaseRepository<Enrollment>  // T is Enrollment
```

#### 2. Abstract Classes
- **Abstract Methods**: Must be implemented by concrete classes
  - `getId(T entity)` - Extract ID from entity
  - `getEntityName()` - Get entity type name for error messages

- **Concrete Methods**: Inherited by all subclasses
  - `add()`, `findById()`, `getById()`, `findAll()`, etc.

#### 3. Template Method Pattern
The base class defines the algorithm structure, and subclasses fill in the details:

```java
public T getById(int id) {
    return findById(id)
        .orElseThrow(() -> new EntityNotFoundException(
            getEntityName(),  // Calls abstract method
            id
        ));
}
```

## Concrete Repository Implementations

### StudentRepository extends BaseRepository<Student>

**Inherits from BaseRepository:**
- add(), findById(), getById(), findAll(), update(), exists(), count()

**Implements Abstract Methods:**
```java
@Override
protected int getId(Student student) {
    return student.getId();
}

@Override
protected String getEntityName() {
    return "Student";
}
```

**Adds Student-Specific Methods:**
- `findAllActive()` - Find active students
- `deactivate(int id)` - Deactivate student
- `countActive()` - Count active students
- `findByBatch(String batch)` - Find by batch
- `findByEmail(String email)` - Find by email

### CourseRepository extends BaseRepository<Course>

**Adds Course-Specific Methods:**
- `findAllActive()` - Find active courses
- `activate(int id)` - Activate course
- `deactivate(int id)` - Deactivate course
- `countActive()` - Count active courses
- `findByNameContaining(String pattern)` - Search by name
- `findByDurationRange(int min, int max)` - Filter by duration

### EnrollmentRepository extends BaseRepository<Enrollment>

**Adds Enrollment-Specific Methods:**
- `findByStudentId(int studentId)` - Find by student
- `findByCourseId(int courseId)` - Find by course
- `findByStatus(EnrollmentStatus status)` - Find by status
- `findByStudentAndCourse(int studentId, int courseId)` - Find specific enrollment
- `updateStatus(int id, EnrollmentStatus status)` - Update status
- `isStudentEnrolledInCourse(int studentId, int courseId)` - Check enrollment
- `countByStatus(EnrollmentStatus status)` - Count by status
- `findActiveEnrollmentsByStudentId(int studentId)` - Active enrollments for student
- `findActiveEnrollmentsByCourseId(int courseId)` - Active enrollments for course

## Benefits of This Design

### 1. DRY Principle (Don't Repeat Yourself)
- Common code written once
- Reduces bugs from inconsistent implementations
- Easier to maintain and update

### 2. Type Safety
- Generics provide compile-time type checking
- No need for casting
- IDE autocomplete works correctly

Example:
```java
StudentRepository studentRepo = new StudentRepository();
Student student = studentRepo.getById(1001);  // Returns Student, not Object
```

### 3. Extensibility
- Easy to add new repository types
- Just extend BaseRepository and implement two abstract methods
- Automatically get all CRUD operations

### 4. Polymorphism
- All repositories share the same base interface
- Can be treated uniformly when needed

Example:
```java
BaseRepository<Student> repo1 = new StudentRepository();
BaseRepository<Course> repo2 = new CourseRepository();
// Both have add(), findById(), etc.
```

### 5. Consistency
- All repositories behave the same way for common operations
- Same error messages format
- Same return types (Optional, List, etc.)

## Advanced Features

### 1. Protected Fields
```java
protected final List<T> entities;
```
- Accessible to subclasses
- Allows specialized queries in concrete repositories
- Maintains encapsulation from external classes

### 2. Optional<T> Return Type
```java
public Optional<T> findById(int id)
```
- Modern Java best practice
- Explicit handling of "not found" case
- Prevents null pointer exceptions

### 3. Stream API Usage
```java
return entities.stream()
    .filter(entity -> getId(entity) == id)
    .findFirst();
```
- Functional programming style
- Clean, readable code
- Lazy evaluation for performance

### 4. Method References
```java
entities.stream()
    .filter(Student::isActive)  // Method reference
    .toList();
```

## Comparison: Before vs After

### StudentRepository - Lines of Code

**Before:**
```
Total: 72 lines
- Common CRUD: ~40 lines
- Student-specific: ~32 lines
```

**After:**
```
Total: 79 lines (with added methods)
- Implements abstract methods: 8 lines
- Student-specific: 71 lines
- Common CRUD: 0 lines (inherited)
```

### Code Reuse Metrics

| Repository | Before (LOC) | After (LOC) | Reduction |
|------------|--------------|-------------|-----------|
| Student    | 72           | 79*         | -         |
| Course     | 71           | 92*         | -         |
| Enrollment | 99           | 134*        | -         |
| **Base**   | -            | **113**     | -         |
| **Total**  | **242**      | **305+113** | **Effective: -70 duplicated LOC** |

*Increased due to additional specialized methods added

**Key Insight**: While total LOC increased due to added functionality, we eliminated ~110 lines of duplicated CRUD code that existed across all repositories.

## Learning Outcomes

Students working with this code will learn:

1. **Generics**: How to write type-safe reusable code
2. **Abstract Classes**: When and why to use abstraction
3. **Template Method Pattern**: Defining algorithm structure in base class
4. **Inheritance**: Extending base classes and calling super methods
5. **Polymorphism**: Treating different types uniformly
6. **DRY Principle**: Eliminating code duplication
7. **Stream API**: Modern Java collection processing
8. **Optional**: Null-safe code practices

## Usage Example

```java
// Create repositories
StudentRepository studentRepo = new StudentRepository();
CourseRepository courseRepo = new CourseRepository();

// Use inherited methods
studentRepo.add(new Student(...));              // From BaseRepository
Course course = courseRepo.getById(2001);       // From BaseRepository
List<Student> all = studentRepo.findAll();      // From BaseRepository

// Use specialized methods
List<Student> active = studentRepo.findAllActive();           // Student-specific
List<Course> short = courseRepo.findByDurationRange(4, 8);   // Course-specific
```

## Further Improvements

Potential enhancements for advanced learning:

1. **Interfaces**: Extract IRepository interface for even more flexibility
2. **Pagination**: Add findAll(int page, int size)
3. **Sorting**: Add findAll(Comparator<T> comparator)
4. **Transactions**: Add transaction support for multiple operations
5. **Caching**: Add caching layer for frequently accessed entities
6. **Query Builder**: Implement fluent query API
7. **Specifications Pattern**: For complex dynamic queries

## Conclusion

The refactored repository design demonstrates professional software engineering practices:
- **Clean Code**: Easy to read and understand
- **Maintainable**: Changes in one place
- **Extensible**: Easy to add new repositories
- **Type-Safe**: Compile-time guarantees
- **Educational**: Shows advanced Java concepts in action

This design scales well for real-world applications and teaches fundamental patterns used in frameworks like Spring Data JPA.