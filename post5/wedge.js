/**
 * Attaches an event listener that looks for barcode scans from a keyboard wedge and
 * executes supplied function upon successful scan. The listener will be attached to
 * the object supplied by the "object" parameter. The "options" parameter configures
 * the behavior of the listener. The default options are defined below:
 *
 * {
 *   startChar: '',
 *   endChar: '',
 *   onScanStart: undefined,
 *   onScanEnd: undefined,
 *   ctrlKey: false,
 *   altKey: false,
 *   shiftKey: false,
 *   metaKey: false
 * }
 * 
 * The startChar, endChar and onScanEnd options are required.
 */
function Wedge(object, options) {

	object.onkeypress = scanCheck;
	var barcodeStart = false;
	var barcodeData = '';

	function scanCheck(e) {

		var c = String.fromCharCode(e.which);

		if (modsMatch(e) && options.startChar == c) {
			barcodeStart = true;
			barcodeData = '';
			if (options.onScanStart !== undefined) {
				options.onScanStart();
			}
		} else if (modsMatch(e) && barcodeStart && options.endChar == c) {
			barcodeStart = false;
			options.onScanEnd(barcodeData);
		} else if (barcodeStart) {
			barcodeData = barcodeData + c;
		}

	}

	// Not XOR function
	function nxor(b1, b2) {
		return (b1 && b2) || (!b1 && !b2);
	}

	// Compare the modifier keys from the options against the key event.
	function modsMatch(e) {
		return nxor(options.ctrlKey, e.ctrlKey) &&
			nxor(options.altKey, e.altKey) &&
			nxor(options.shiftKey, e.shiftKey) &&
			nxor(options.metaKey, e.metaKey);
	}

}
