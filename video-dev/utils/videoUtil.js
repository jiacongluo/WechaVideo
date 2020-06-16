
function uploadVideo() {
  var me = this;

  wx.chooseVideo({
    sourceType: ['album'],
    success: function(res) {
      console.log(res);
      var duration = res.duration;
      var tmpHeight = res.height;
      var tmpVideoUrl = res.tempFilePath;
      var tmpCoverUrl = res.thumbTempFilePath;
      var tmpWidth = res.width;

      if (duration > 30) {
        wx.showToast({
          title: '视频长度不能超过10秒',
          icon: "none",
          duration: 2500
        })
      } else if (duration < 1) {
        wx.showToast({
          title: '视频长度必须大于1秒...',
          icon: "none",
          duration: 2500
        })
      } else {
        //打开bgm选择页面
        wx.navigateTo({
          url: '../chooseBgm/chooseBgm?duration=' + duration +
            "&tmpHeight=" + tmpHeight +
            "&tmpWidth=" + tmpWidth +
            "&tmpVideoUrl=" + tmpVideoUrl +
            "&tmpCoverUrl=" + tmpCoverUrl,
        })
      }

    }
  })
}


module.exports={
  uploadVideo:uploadVideo
}