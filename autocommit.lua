--Every 15 minutes, run
--[[
	rm assets and ds
	cp assets and ds from MCP
	git add .
	git commit -m "15 minute commit"
	git push
]]
function commit()
	os.remove("assets")
	os.remove("ds")
	os.execute('cp "C:\Users\Dwayne\Games\MCForge\1.6.2_804\forge\mcp\src\minecraft\assets" assets')
	os.execute('cp "C:\Users\Dwayne\Games\MCForge\1.6.2_804\forge\mcp\src\minecraft\ds" ds')
	os.execute('git add .')
	os.execute('git commit -m "15 minute commit"')
	os.execute('git push')
end

while true do
	local lasttime = os.time()
	while os.time()-lasttime < 60*15 do
		
	end
	commit()
end
