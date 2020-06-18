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
function compress(img, fileType, maxWidth) {
    // html5 <canvas>标签用于绘制图像， 仅仅是图像的容器，必须使用脚本完成实际的绘图任务
    let canvas = document.createAttribute('canvas')
    // getContext()方法可返回一个对象，该对象提供用于在画布上绘图的方法和属性
    let ctx = canvas.getContext('2d')

    const proportion = img.width / img.height
    const width = maxWidth
    const height = maxWidth / proportion

    canvas.width = width   // 返回ImageData对象的宽度
    canvas.height = height  // // 返回ImageData对象的高度

    ctx.fillStyle = '#fff'  // 设置用于填充绘画的颜色、渐变或模式
    ctx.fillRect(0,0,canvas.width, canvas.height)  // 绘制“被填充”矩形
    ctx.drawImage(img, 0, 0, width, height)   // 向画布上绘制图像、画布或视频
    const base64data = canvas.toDataURL(fileType, 0.75)  // 返回图像的base64字符串
    canvas = ctx = null

    return base64data
}

/* 选择图片函数
 * @Params   e  input.onchange事件对象
  *          cb  回调函数
  *          maxsize    图片最大体积*/
function chooseImg(e, cb, maxsize = 200 * 1024) {
    const  file = e.target.files[0]  // 得到的是第一张input选择的图片的一些参数
    if (!file || !/\/(?:jpeg|jpg|png)/i.test(file.type)) {
        return
    }
    const reader = new FileReader()
        reader.onload = function () {
            const result = this.result
            let img = new Image()

            if (result.length <= maxsize){
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


