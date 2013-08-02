--Every 15 minutes, run
--[[
	rm assets and ds
	cp assets and ds from MCP
	git add .
	git commit -m "15 minute commit"
	git push
]]
function commit()
	os.execute("rm ./assets")
	os.execute("rm ./ds")
	os.execute("mkdir assets")
	os.execute("mkdir ds")
	os.execute('xcopy "C:/Users/Dwayne/Games/MCForge/1.6.2_804/forge/mcp/src/minecraft/assets" assets /e /i /h')
	os.execute('xcopy "C:/Users/Dwayne/Games/MCForge/1.6.2_804/forge/mcp/src/minecraft/ds" ds /e /i /h')
	os.execute('git add .')
	os.execute('git commit -m "15 minute commit"')
	os.execute('git push')
end

while true do
	commit()
	os.execute("sleep "..(60*15))
end
