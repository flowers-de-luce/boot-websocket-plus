/* 粘贴图片
 * 为更好在输入框中展示图片，必须限制图片大小，
 * 所以该图片处理函数不仅能够读取File对象中的图片，还能对其进行良好的压缩 */

/* 预览函数
 * 参数： dataUrl  base64字符串
  *       cb   回调函数
  *       */
function toPreviewer (dataUrl, cb) {
    cb && cb(dataUrl)
}

/* 图片压缩函数
 * @Params： img 图片对象
 *        fileType 图片类型
 *        maxWidth 图片最大宽度
 * @return   base64字符串
 * */
function compress (img, fileType, maxWidth) {
    let canvas = document.createElement('canvas')
    let ctx = canvas.getContext('2d')

    const proportion = img.width / img.height
    const width = maxWidth
    const height = maxWidth / proportion

    canvas.width = width
    canvas.height = height

    ctx.fillStyle = '#fff'
    ctx.fillRect(0, 0, canvas.width, canvas.height)
    ctx.drawImage(img, 0, 0, width, height)

    const base64data = canvas.toDataURL(fileType, 0.75)
    canvas = ctx = null

    return base64data
}

/* 选择图片函数
 * @Params   e  input.onchange事件对象
  *          cb  回调函数
  *          maxsize    图片最大体积*/
// function chooseImg (e, cb, maxsize = 200 * 1024) {
function chooseImg (e, cb, maxsize = 200 * 200) {
    const file = e.target.files[0]

    if (!file || !/\/(?:jpeg|jpg|png)/i.test(file.type)) {
        return
    }

    const reader = new FileReader()
    reader.onload = function () {
        const result = this.result
        let img = new Image()

        if (result.length <= maxsize) {
            toPreviewer(result, cb)
            return
        }

        img.onload = function () {
            const compressedDataUrl = compress(img, file.type, maxsize / 1024)
            toPreviewer(compressedDataUrl, cb)
            img = null
        }

        img.src = result
    }

    reader.readAsDataURL(file)
}