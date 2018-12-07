SELECT '人数' as '分类',COUNT(*) FROM t_user
UNION
SELECT '收入',COUNT(*)*70 FROM t_user
UNION
SELECT '打出去的',SUM(t.money) FROM t_cash_history t