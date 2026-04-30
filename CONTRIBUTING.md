# Contributing to Online Course Enrollment System

Thank you for considering contributing to this project! We welcome contributions from everyone.

## How to Contribute

### Reporting Bugs

If you find a bug, please create an issue with:
- Clear description of the bug
- Steps to reproduce
- Expected behavior
- Actual behavior
- Screenshots (if applicable)
- Your environment (OS, Java version, database version)

### Suggesting Enhancements

Enhancement suggestions are welcome! Please create an issue with:
- Clear description of the enhancement
- Why this enhancement would be useful
- Possible implementation approach

### Pull Requests

1. **Fork the repository**
   ```bash
   git clone https://github.com/yourusername/course-enrollment-system.git
   ```

2. **Create a feature branch**
   ```bash
   git checkout -b feature/AmazingFeature
   ```

3. **Make your changes**
   - Write clean, readable code
   - Follow existing code style
   - Add comments where necessary
   - Update documentation if needed

4. **Test your changes**
   - Ensure all existing functionality still works
   - Test your new feature thoroughly
   - Check for any compilation errors

5. **Commit your changes**
   ```bash
   git add .
   git commit -m "Add some AmazingFeature"
   ```
   
   Commit message guidelines:
   - Use present tense ("Add feature" not "Added feature")
   - Use imperative mood ("Move cursor to..." not "Moves cursor to...")
   - First line should be 50 characters or less
   - Reference issues and pull requests after the first line

6. **Push to your fork**
   ```bash
   git push origin feature/AmazingFeature
   ```

7. **Open a Pull Request**
   - Provide a clear description of the changes
   - Reference any related issues
   - Include screenshots for UI changes

## Code Style Guidelines

### Java Code
- Use meaningful variable and method names
- Follow Java naming conventions (camelCase for variables/methods, PascalCase for classes)
- Add JavaDoc comments for public methods and classes
- Keep methods focused and concise
- Use proper exception handling

### Database
- Use meaningful table and column names
- Add appropriate indexes
- Include foreign key constraints
- Document schema changes

### Web (JSP/HTML/CSS)
- Use semantic HTML
- Keep CSS organized and commented
- Ensure responsive design
- Test across different browsers

## Development Setup

1. Install prerequisites:
   - JDK 11 or higher
   - MySQL 8.0+ (or use H2 for testing)
   - Apache Tomcat 10.x (optional)

2. Clone and setup:
   ```bash
   git clone https://github.com/yourusername/course-enrollment-system.git
   cd course-enrollment-system
   ```

3. Configure database:
   - Update `src/database/DatabaseConnection.java` with your credentials
   - Run `database/sample_data.sql` to create tables

4. Build and run:
   ```bash
   ./compile-and-run.sh  # Linux/Mac
   compile-and-run.bat   # Windows
   ```

## Testing

Before submitting a pull request:
- Test all enrollment scenarios
- Verify eligibility checking works correctly
- Test duplicate enrollment prevention
- Check seat management functionality
- Test both web and admin interfaces
- Verify database operations

## Areas for Contribution

We especially welcome contributions in these areas:
- [ ] Unit tests (JUnit)
- [ ] Integration tests
- [ ] User authentication system
- [ ] Payment gateway integration
- [ ] Email notification system
- [ ] RESTful API development
- [ ] Mobile-responsive improvements
- [ ] Accessibility enhancements
- [ ] Performance optimizations
- [ ] Documentation improvements

## Questions?

Feel free to create an issue with the "question" label if you need help or clarification.

## Code of Conduct

- Be respectful and inclusive
- Welcome newcomers
- Focus on constructive feedback
- Help others learn and grow

Thank you for contributing! 🎓
