/* 处理富文本输入框内容，
 * 两类数据： 普通的文本数据类型，包括emoji表情
  *           图片类型数据
  * */
const onPase = (e) =>{
    // 剪贴板的内容存放在DataTransferItemList对象中，可以通过DataTransferItemList 
    if (!(e.clipboardData && e.clipboardData.items)){
        return
    }

    // Promise是一个构造函数，有resolve（解决）, reject（拒绝）, race（拒绝）等静态方法
    // 用Promise封装便于将来使用
    return new Promise((resolve, reject) => {
        // 复制的内容在剪贴板里位置不确定，所以通过遍历来保证数据准确
        for (let i = 0, len = e.clipboardData.items.length; i < len; i++) {
            const item = e.clipboardData.items[i]
            // 文本格式内容处理
            if (item.kind === 'string') {
                item.getAsString((str) => {
                    resolve(str)
                })
                // 图片格式内容处理
            } else if (item.kind === 'file') {
                const pasteFile = item.getAsFile()
                // 处理pasteFile
                // TODO(pasteFile)
                const imgEvent = {
                    target: {
                        files: [pasteFile]
                    }
                }
                chooseImg(imgEvent, (url) => {
                    resolve(url)
                })
            } else {
                reject(new Error('Not allow to paste this type!'))
            }
        }
    })
}