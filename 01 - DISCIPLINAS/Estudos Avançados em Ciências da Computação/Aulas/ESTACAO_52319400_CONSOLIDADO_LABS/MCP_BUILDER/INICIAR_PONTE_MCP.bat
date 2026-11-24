@echo off
chcp 65001 >nul
title PONTE MCP DO CISCO PACKET TRACER
echo Iniciando a ponte local em 127.0.0.1:54321...
echo Mantenha esta janela aberta enquanto usar o MCP BUILDER.
echo.
python -c "from packet_tracer_mcp.infrastructure.execution.live_bridge import PTCommandBridge; import time; b=PTCommandBridge(); b.start(); print('PONTE MCP ATIVA EM 127.0.0.1:54321'); time.sleep(31536000)"
echo.
echo A ponte foi encerrada ou encontrou um erro.
pause
