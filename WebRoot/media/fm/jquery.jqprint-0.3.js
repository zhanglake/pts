// -----------------------------------------------------------------------
// Eros Fratini - eros@recoding.it
// jqprint 0.3
//
// - 19/06/2009 - some new implementations, added Opera support
// - 11/05/2009 - first sketch
//
// Printing plug-in for jQuery, evolution of jPrintArea: http://plugins.jquery.com/project/jPrintArea
// requires jQuery 1.3.x
//
// Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
//------------------------------------------------------------------------

;(function($) {

	$.fn.jqprint = function (options) {
		var opt = $.extend({}, $.fn.jqprint.defaults, options);

		var $element = (this instanceof jQuery) ? this : $(this);

		var strFrameName = "printIframe-" + (new Date()).getTime();


		if (window.location.hostname !== document.domain && navigator.userAgent.match(/msie/i)) {
			// Ugly IE hacks due to IE not inheriting document.domain from parent
			// checks if document.domain is set by comparing the host name against document.domain
			var iframeSrc = "javascript:document.write(\"<head><script>document.domain=\\\"" + document.domain + "\\\";</script></head><body></body>\")";
			var printI = document.createElement('iframe');
			printI.name = "printIframe";
			printI.id = strFrameName;
			printI.className = "MSIE";
			document.body.appendChild(printI);
			printI.src = iframeSrc;

		} else {
			var $printFrame = $("<iframe id='" + strFrameName + "' name='printIframe' ></iframe>");
			$printFrame.appendTo("body");
		}


		var $iprintFrame = $("#" + strFrameName);

		if (!opt.debug) {
			$iprintFrame.css({ position: "absolute", width: "0px", height: "0px", left: "-600px", top: "-600px" });
		}

		var win, doc;
		win = $iprintFrame.get(0);
		win = win.contentWindow || win.contentDocument || win;
		doc = win.document || win.contentDocument || win;

		var $doc = $iprintFrame.contents(),
			$head = $doc.find("head"),
			$body = $doc.find("body");

		if (opt.importCSS) {
			if ($("link[media=print]").length > 0) {
				$("link[media=print]").each(function() {
					doc.write("<link type='text/css' rel='stylesheet' href='" + $(this).attr("href") + "' media='print' />");
				});
			} else {
				$("link").each(function() {
					doc.write("<link type='text/css' rel='stylesheet' href='" + $(this).attr("href") + "' />");
				});
			}
		}

		if (opt.printContainer) {
			doc.write($element.outer());
		} else {
			$element.each(function() { doc.write($(this).html()); });
		}

		doc.close();

		$iprintFrame.focus();

		setTimeout(function() {
			if ($iprintFrame.hasClass("MSIE")) {
				// check if the iframe was created with the ugly hack
				// and perform another ugly hack out of neccessity
				window.frames["printIframe"].focus();
				$head.append("<script>  window.print(); </script>");
			} else {
				// proper method
				if (document.queryCommandSupported("print")) {
					$iprintFrame[0].contentWindow.document.execCommand("print", false, null);
				} else {
					$iprintFrame[0].contentWindow.focus();
					$iprintFrame[0].contentWindow.print();
				}
			}
		}, 1000);
	};
	
	$.fn.jqprint.defaults = {
		debug : false,
		importCSS : true, 
		printContainer : true
	};

	// Thanks to 9__, found at http://users.livejournal.com/9__/380664.html
	jQuery.fn.outer = function() {
		return $($('<div></div>').html(this.clone())).html();
	};
	
	
})(jQuery);