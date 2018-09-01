
//当扣费类型变化的时候
function changeDeductionType(obj, show) {
    var thisObj = $(obj)
    log("thisObj", thisObj.val())
    changeDeductionTypeValue(thisObj.val(), show)
}

//当扣费类型的值变化时
function changeDeductionTypeValue(value, show) {
    //有效期内免费
    if (value == '4') {
        show.initialMoney = false
        show.initialNumber = false
    }
    //按次扣费
    if (value == '3') {
        show.initialMoney = false
        show.initialNumber = true
    }
    //按金额扣费
    if (value == '2') {
        show.initialMoney = true
        show.initialNumber = false
    }
}
