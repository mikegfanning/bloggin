/**
 * Attaches an event listener that looks for barcode scans from a keyboard wedge and
 * executes supplied function upon successful scan. The listener will be attached to
 * the object supplied by the "object" parameter. The "options" parameter configures
 * the behavior of the listener. The default options are defined below:
 *
 * {
 *   startSeq: [],
 *   endSeq: [],
 *   onScanStart: undefined,
 *   onScanEnd: undefined,
 * }
 * 
 * The "startSeq" and "endSeq" options are arrays of key codes that a keyboard wedge
 * barcode scanner will send before and after barcode data. The "onScanStart" option
 * is not required.
 */
function Wedge(object, options) {

    var scanning = false;
    var seqIndex = 0;
    var barcodeData = [];
    object.onkeypress = keyHandler;

    function keyHandler(e) {
        console.log(e);
        if (scanning) {
            barcodeData.push(e.keyCode);
            seqCompare(e.keyCode, options.endSeq, function() {
                scanning = false;

                // Trim end sequence from barcode data and invoke end callback.
                var l = barcodeData.length - options.endSeq.length;
                options.onScanEnd(barcodeData.slice(0, l));
                barcodeData = [];
            });
        } else {
            seqCompare(e.keyCode, options.startSeq, function() {
                scanning = true;
                if (options.onScanStart !== undefined) {
                    options.onScanStart();
                }
            });
        }
    }

    // Check to see if we're scanning a start or end sequence. "func" is a callback
    // to execute when we get to the end of the sequence.
    function seqCompare(keyCode, seq, func) {
        if (keyCode != seq[seqIndex]) {
            seqIndex = (seq[0] == keyCode) ? 1 : 0;
        } else if (++seqIndex == seq.length) {
            func();
            seqIndex = 0;
        }
    }
}
