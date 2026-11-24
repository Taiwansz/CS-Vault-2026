import asyncio

from mcp import ClientSession
from mcp.client.streamable_http import streamablehttp_client


async def texto(resultado):
    return "\n".join(getattr(item, "text", "") for item in resultado.content)


async def main():
    async with streamablehttp_client("http://127.0.0.1:39000/mcp") as (read, write, _):
        async with ClientSession(read, write) as sessao:
            await sessao.initialize()

            etapas = [
                ("pt_delete_device", {"device_name": "Power Distribution Device0"}),
                ("pt_query_topology", {}),
                ("pt_health_check", {}),
                (
                    "pt_verify_connectivity",
                    {"from_device": "PC1", "to_ip": "192.168.20.10", "timeout_s": 25},
                ),
                (
                    "pt_verify_connectivity",
                    {"from_device": "PC2", "to_ip": "192.168.10.10", "timeout_s": 25},
                ),
            ]
            for nome, argumentos in etapas:
                resultado = await sessao.call_tool(nome, argumentos)
                print(f"\n### {nome}\n{await texto(resultado)}")

            for roteador in ("R1", "R2", "R3"):
                codigo = (
                    f'var d=ipc.network().getDevice("{roteador}");'
                    'var cl=d.getCommandLine();'
                    'var p=String(cl.getPrompt()||"");'
                    'if(p.indexOf("[yes/no]")>=0){cl.enterCommand("no");}'
                    'cl.enterCommand("");'
                    'cl.enterCommand("enable");'
                    'cl.enterCommand("show ip ospf neighbor");'
                    'cl.enterCommand("show ip route ospf");'
                    'cl.enterCommand("show ip ospf interface brief");'
                    'cl.enterCommand("show ip interface brief");'
                )
                await sessao.call_tool("pt_send_raw", {"js_code": codigo})
                await asyncio.sleep(1)
                leitura = (
                    f'var d=ipc.network().getDevice("{roteador}");'
                    'var o=String(d.getCommandLine().getOutput());'
                    'reportResult(o.slice(-12000));'
                )
                resultado = await sessao.call_tool("pt_send_raw", {"js_code": leitura, "wait_result": True})
                print(f"\n### {roteador} CLI\n{await texto(resultado)}")

            resultado = await sessao.call_tool(
                "pt_save_project",
                {
                    "filename": "Atividade_2_OSPF.pkt",
                    "directory": "C:/Users/52319400",
                },
            )
            print(f"\n### SAVE\n{await texto(resultado)}")


if __name__ == "__main__":
    asyncio.run(main())
