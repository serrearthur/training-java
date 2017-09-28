//On load
$(function() {
	// Default: hide edit mode
	$(".editMode").hide();

	// Click on "selectall" box
	$("#selectall").click(function () {
		$('.cb').prop('checked', this.checked);
	});

	// Click on a checkbox
	$(".cb").click(function() {
		if ($(".cb").length == $(".cb:checked").length) {
			$("#selectall").prop("checked", true);
		} else {
			$("#selectall").prop("checked", false);
		}
		if ($(".cb:checked").length != 0) {
			$("#deleteSelected").enable();
		} else {
			$("#deleteSelected").disable();
		}
	});
});

//Function setCheckboxValues
(function ( $ ) {
	$.fn.setCheckboxValues = function(formFieldName, checkboxFieldName) {
		var str = $('.' + checkboxFieldName + ':checked').map(function() {
			return this.value;
		}).get().join();

		$(this).attr('value',str);
		return this;
	};
}( jQuery ));

//Function toggleEditMode
(function ( $ ) {
	$.fn.toggleEditMode = function() {
		if($(".editMode").is(":visible")) {
			$(".editMode").hide();
			$("#editComputer").text("Edit");
		}
		else {
			$(".editMode").show();
			$("#editComputer").text("View");
		}
		return this;
	};
}( jQuery ));

//Function delete selected: Asks for confirmation to delete selected computers, then submits it to the deleteForm
(function ( $ ) {
	$.fn.deleteSelected = function() {
		if ($(".cb:checked").length != 0 && confirm("Are you sure you want to delete the selected computers?")) { 
			$('#deleteForm input[name=selection]').setCheckboxValues('selection','cb');
			$('#deleteForm').submit();
		}
	};
}( jQuery ));

//Event handling : Onkeydown
$(document).keydown(function(e) {
	switch (e.keyCode) {
	//DEL key
	case 46:
		if($(".editMode").is(":visible") && $(".cb:checked").length != 0) {
			$.fn.deleteSelected();
		}   
		break;
		//E key (CTRL+E will switch to edit mode)
	case 69:
		if(e.ctrlKey) {
			$.fn.toggleEditMode();
		}
		break;
	}
});

//Table sorting functions
$("th.sortable").click(function(){
	var table = $(this).parents('table').eq(0)
    var rows = table.find('tr:gt(0)').toArray().sort(comparer($(this).index()))
    this.asc = !this.asc
    if (!this.asc){rows = rows.reverse()}
    for (var i = 0; i < rows.length; i++){table.append(rows[i])}
})
function comparer(index) {
    return function(a, b) {
        var valA = getCellValue(a, index), valB = getCellValue(b, index)
        return $.isNumeric(valA) && $.isNumeric(valB) ? valA - valB : valA.localeCompare(valB)
    }
}
function getCellValue(row, index){ return $(row).children('td').eq(index).text() }