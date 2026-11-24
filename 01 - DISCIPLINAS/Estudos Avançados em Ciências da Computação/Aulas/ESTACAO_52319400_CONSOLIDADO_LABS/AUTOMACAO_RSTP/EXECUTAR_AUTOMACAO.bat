@echo off
chcp 65001 >nul
cd /d "%~dp0"
python -c "import pyautogui, pyperclip" 2>nul || python -m pip install pyautogui pyperclip
python criar_atividade_rstp.py
echo.
pause
