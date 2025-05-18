# GitHub SSH Setup & Push Guide

This guide will help you set up SSH keys for GitHub and push your code securely.

---

## 1. Generate a New SSH Key

Open your terminal and run:
```bash
ssh-keygen -t ed25519 -C "your_email@example.com"
```
- When prompted, press Enter to accept the default file location.
- Enter a secure passphrase (optional, but recommended).

## 2. Add Your SSH Key to the SSH Agent

Start the ssh-agent in the background:
```bash
eval "$(ssh-agent -s)"
```

Add your SSH private key to the agent:
```bash
ssh-add ~/.ssh/id_ed25519
```

## 3. Add the SSH Key to Your GitHub Account

Copy your public key to the clipboard:
```bash
cat ~/.ssh/id_ed25519.pub
```

- Go to [GitHub SSH Keys Settings](https://github.com/settings/keys)
- Click **New SSH key**
- Paste the copied key into the field
- Give it a descriptive title and save

## 4. Test Your SSH Connection

Run:
```bash
ssh -T git@github.com
```
- You should see a message like: `Hi <username>! You've successfully authenticated...`

## 5. Set the Remote URL to Use SSH

If your remote is not already set to SSH, update it:
```bash
git remote set-url origin git@github.com:<your-username>/<your-repo>.git
```

Check your remote:
```bash
git remote -v
```

## 6. Push Your Code to GitHub

Add and commit your changes:
```bash
git add .
git commit -m "your commit message"
```

Push to your branch:
```bash
git push origin <branch-name>
```

If you need to force push (use with caution):
```bash
git push origin <branch-name> --force
```

---

## Troubleshooting

- **Permission denied (publickey)**: Make sure your public key is added to GitHub and the private key is loaded in the agent.
- **Multiple SSH keys**: Use `ssh-add` to add the correct key, or specify which key to use in `~/.ssh/config`.
- **Passphrase prompt**: Enter the passphrase you set during key generation.

---

## References
- [GitHub: Connecting to GitHub with SSH](https://docs.github.com/en/authentication/connecting-to-github-with-ssh)
- [GitHub: Generating a new SSH key](https://docs.github.com/en/authentication/connecting-to-github-with-ssh/generating-a-new-ssh-key-and-adding-it-to-the-ssh-agent) 