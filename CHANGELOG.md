# Changelog

All notable version notes will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/).

## [1.5.0] - Unreleased

### Added
- Add JaCoCo to measure the code coverage.
- Add fmt-plugin to format the code with the Google Java Style and format the code according to this.
- Add dependabot to keep the dependencies up to date.
- Add CHANGELOG.md to keep track of the changes in the application (removed from README.md).
- 
### Changed
- Update Spring Boot to 3.2.2

## [1.4.0] - 2024/01/24

### Fixed
- Restructured the rest package and moved to infrastructure to fix architecture.

### Added
- Added GitHub Actions to run the tests and build the application.

### Changed
- Refactored some naming to improve the readability of the code.

## [1.3.0] - 2024/01/09

### Changed
- Updated README.
- Improved code: Refactored the CLI to improve readability.

## [1.2.0] - 2023/12/29

### Fixed
- Fixed README markdowns.

### Changed
- Updated Spring Boot to 3.2.1.
- Updated SpringDoc OpenApi to 2.3.0.
- Improved code: Refactored the tests to improve the readability of the code and added more tests.

## [1.1.0] - 2023/12/21

### Fixed
- CLI bug fix: Fixed the problem that did not correctly display the positions of the mowers when completing the execution of the instructions in the CLI.

### Added
- Added versions in README and upgraded correctly the pom.
- Added actuator timed to measure the time of the execution of the instructions.
- Added more logs to facilitate the debugging of the application.
- Added pdf with the code challenge description.

### Changed
- Improved code: Some method refactoring names to improve the readability of the code.

## [1.0.0] - 2023/12/10

### Added
- Initial application launch.