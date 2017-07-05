//清除表单
function clearFormTsk() {
	$('#taskinfo').form('clear');
	$('#newTask').dialog('close');
	$('#username').val("");
	$('#firstRun').val("");
	
}
	function clearView() {
		$('#viewTskName').text("");
		$('#viewUser').text("");
		$('#viewIp').text("");
		$('#viewIns').text("");
		$('#viewFp').text("");
		$('#viewTskTime').text("");
		$('#viewTskCycle').text("");
		$('#viewExpStatus').text("");
		$('#viewZipStatus').text("");

	}