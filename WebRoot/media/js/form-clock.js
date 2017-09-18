var  FormClock = function() {
	var handleClockfaceTimePickers = function () {

        if (!jQuery().clockface) {
            return;
        }

        $('.clockface_1').clockface();

        $('#totm').clockface({
            format: 'HH:mm',
            trigger: 'manual'
        });

        $('#totm_toggle').click(function (e) {
            e.stopPropagation();
            $('#totm').clockface('toggle');
        });

        $('#frmtm').clockface({
            format: 'HH:mm',
            trigger: 'manual'
        });

        $('#frmtm_toggle').click(function (e) {
            e.stopPropagation();
            $('#frmtm').clockface('toggle');
        });

        $('.clockface_3').clockface({
            format: 'H:mm'
        }).clockface('show', '14:30');
    }
	return {
        //main function to initiate the module
        init: function () {
            handleClockfaceTimePickers();
        }

    };
}();