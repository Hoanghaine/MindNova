# MindNova Deployment & CI/CD Overview

## 1. Nginx
- Reverse proxy từ port 80/443 sang Spring Boot app.
- Kết hợp với SSL từ Certbot.

## 2. Certbot
- Cấp và tự động renew certificate cho HTTPS.

## 3. GitHub Actions
- Trigger: push tag `v*`.
- Build Spring Boot `.jar`.
- Tạo release và upload `.jar`.
- Không dùng SSH, chỉ build & release.

## 4. nssm
- Chạy Spring Boot `.jar` như Windows Service.
- Cập nhật jar: stop/start service.

## 5. Auto-Update Script
- PowerShell script trên VPS:
    - Download `.jar` mới từ GitHub release.
    - Stop/start service nssm.
- Chạy thủ công hoặc qua Scheduled Task.

## 6. CI/CD Flow
1. Dev push tag → GitHub Actions build & release jar.
2. VPS script download jar → restart service.
3. Nginx + Certbot đảm bảo HTTPS.

**Đặc điểm:**
- Tự động build & release.
- VPS tự pull jar, service luôn chạy.
- Không cần SSH/key.
