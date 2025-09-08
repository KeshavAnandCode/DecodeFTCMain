# Team FTC Git Workflow Guide
    
Hello!

This document explains how to use this repo as a 23344 team member


---

## 1. Cloning the Repository

1. Open a terminal (or the terminal inside Android Studio).
2. Navigate to the folder where you want to keep the project.
3. Run:

   ```bash
   git clone https://github.com/KeshavAnandCode/DecodeFTCMain.git
   cd DecodeFTCMain
   ```

4. Verify your remotes:

   ```bash
   git remote -v
   ```

   You should see:
   ```
   origin    https://github.com/KeshavAnandCode/DecodeFTCMain.git (fetch)
   origin    https://github.com/KeshavAnandCode/DecodeFTCMain.git (push)
   upstream  https://github.com/FIRST-Tech-Challenge/FtcRobotController.git (fetch)
   upstream  https://github.com/FIRST-Tech-Challenge/FtcRobotController.git (push)
   ```

---

## 2. Keeping `master` Clean

- `master` should only contain clean, tested code.
- Nobody should ever code directly on `master`.
- To stay up to date:

   ```bash
   git checkout master
   git fetch upstream
   git merge upstream/master
   git push origin master
   ```

---

## 3. Creating a Feature Branch

Whenever you start a new task (feature, fix, experiment):

1. Update `master` (see above).
2. Create a new branch from `master`:

   ```bash
   git checkout master
   git pull origin master
   git checkout -b feature/short-description
   ```

### Branch Naming Standard

Branches **must** follow the format:

```
<type>/<short-description>
```

Where `<type>` is one of:
- `feature/` → new functionality
- `fix/` → bug fixes
- `experiment/` → prototypes or tests
- `docs/` → documentation updates
- `chore/` → maintenance or cleanup

Examples:
- `feature/autonomous-path`
- `fix/motor-init`
- `experiment/vision-test`
- `docs/setup-instructions`
- `chore/gradle-update`

**Rules for names:**
- Use lowercase letters and hyphens (`-`) only.
- Keep it short but clear (3–5 words).
- One branch = one task. Never mix unrelated work.

---

## 4. Working on Your Branch

- Make changes in Android Studio.
- Stage and commit your changes:

   ```bash
   git add .
   git commit -m "short message about what changed"
   ```

- Push your branch to GitHub:

   ```bash
   git push origin feature/short-description
   ```

---

## 5. Sharing Your Work

- Once your branch is ready:
    1. Open a Pull Request (PR) on GitHub to merge into `master`.
    2. At least one teammate should review before merging.

---

## 6. Branching Rules

**Do:**
- Always branch from `master`.
- Follow the naming standard exactly.
- Keep branches small and focused.
- Delete branches after they’re merged.

**Don’t:**
- Don’t push commits directly to `master`.
- Don’t leave unfinished work on `master`.
- Don’t mix unrelated changes in one branch.

---

## 7. Example Workflow

```bash
# Get latest code
git checkout master
git fetch upstream
git merge upstream/master
git push origin master

# Start a new feature
git checkout -b feature/teleop-improvements

# Work on code, then commit
git add .
git commit -m "improved joystick scaling in TeleOp"

# Push branch
git push origin feature/teleop-improvements
```
