-- MISSÃO 4 — Radar de Scout (SQL Read)
-- 1) Selecione id, nome, posicao, salario da tabela atletas
-- 2) Filtre apenas posicao = 'Atacante' AND status = 'Ativo'
-- 3) Ordene por salario DESC

SELECT id, nome, posicao, salario
FROM atletas
WHERE posicao = 'Atacante' AND status = 'Ativo'
ORDER BY salario DESC;
