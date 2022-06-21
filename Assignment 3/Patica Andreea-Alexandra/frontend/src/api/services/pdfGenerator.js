class ByteToDocument {
    static base64ToArrayBuffer (base64) {
        var binaryString = window.atob(base64)
        var binaryLen = binaryString.length
        var bytes = new Uint8Array(binaryLen)
        for (var i = 0; i < binaryLen; i++) {
            bytes[i] = binaryString.charCodeAt(i)
        }
        return bytes.buffer
    }

    static saveByteArray (reportName, byte) {
        var blob = new Blob([byte], { type: 'application/pdf' })
        var link = document.createElement('a')
        link.href = window.URL.createObjectURL(blob)
        var fileName = reportName
        link.download = fileName
        link.click()
    }
}

export default ByteToDocument