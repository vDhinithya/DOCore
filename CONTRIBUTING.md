# Contributing to DOCore

First off, thanks for taking the time to contribute! 🎉

## Development Workflow

We follow a **Trunk-Based Development** workflow with Feature Branches.

1.  **Fork & Clone:** Fork the repo and clone it locally.
2.  **Branching:** Create a branch for your feature (`feat/my-feature`) or bugfix (`fix/npe-crash`).
3.  **Local Development:**
    * Run infrastructure: `docker compose -f docker-compose-dev.yml up -d`
    * Run Java apps via IntelliJ or Maven.
4.  **Testing:** Ensure the application builds cleanly with `mvn clean package`.
5.  **Commit Messages:** We use **Conventional Commits**:
    * `feat:` New features
    * `fix:` Bug fixes
    * `docs:` Documentation changes
    * `ci:` CI/CD configuration
6.  **Pull Request:** Push your branch and open a PR against `main`.

## Definition of Done (DoD)
* [ ] Code builds without errors.
* [ ] No sensitive data (API keys, passwords) leaked.
* [ ] README updated if architectural changes were made.