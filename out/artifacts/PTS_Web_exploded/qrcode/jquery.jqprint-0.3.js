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

; (function ($) {

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

		// Add doctype to fix the style difference between printing and render
		function setDocType($iframe, doctype) {
			var win, doc;
			win = $iframe.get(0);
			win = win.contentWindow || win.contentDocument || win;
			doc = win.document || win.contentDocument || win;
			doc.open();
			doc.write(doctype);
			doc.close();
		}

		if (opt.doctypeString) {
			setDocType($iprintFrame, opt.doctypeString);
		}

		var $doc = $iprintFrame.contents(),
			$head = $doc.find("head"),
			$body = $doc.find("body");

		// add base tag to ensure elements use the parent domain
		//$head.append('<base href="' + document.location.protocol + '//' + document.location.host + '">');

		// import page stylesheets
		if (opt.importCSS) $("link[rel=stylesheet]").each(function () {
			var href = $(this).attr("href");
			if (href) {
				var media = $(this).attr("media") || "all";
				$head.append("<link type='text/css' rel='stylesheet' href='" + href + "' media='" + media + "'>")
			}
		});

		// import style tags
		if (opt.importStyle) $("style").each(function () {
			$(this).clone().appendTo($head);
			//$head.append($(this));
		});

		// import additional stylesheet(s)
		if (opt.loadCSS) {
			if ($.isArray(opt.loadCSS)) {
				jQuery.each(opt.loadCSS, function (index, value) {
					$head.append("<link type='text/css' rel='stylesheet' href='" + this + "'>");
				});
			} else {
				$head.append("<link type='text/css' rel='stylesheet' href='" + opt.loadCSS + "'>");
			}
		}


		// grab $.selector as container
		if (opt.printContainer) $body.append($element.outer());

			// otherwise just print interior elements of container
		else $element.each(function () {
			$body.append($(this).html());
		});



		$iprintFrame.focus();

		setTimeout(function () {
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
		debug: false,
		importCSS: true,
		importStyle: false,
		loadCSS: "",
		printContainer: true,
		doctypeString: '<!DOCTYPE html>' // html doctype
	};

	// Thanks to 9__, found at http://users.livejournal.com/9__/380664.html
	jQuery.fn.outer = function () {
		return $($('<div></div>').html(this.clone())).html();
	};


})(jQuery);