const app = getApp()

Page({
  data: {
    danmuList: [
      {
        text: '第 1s 出现的弹幕',
        color: '#ff0000',
        time: 1
      },
      {
        text: '第 3s 出现的弹幕',
        color: '#ff00ff',
        time: 3
      },{
        text: '第 5s 出现的弹幕',
        color: '#ff00ff',
        time: 5
      },{
        text: '第 7s 出现的弹幕',
        color: '#ff00ff',
        time: 7
      },{
        text: '第 9s 出现的弹幕',
        color: '#ff00ff',
        time: 9
      },{
        text: '第 11s 出现的弹幕',
        color: '#ff00ff',
        time: 11
      },{
        text: '第 13s 出现的弹幕',
        color: '#ff00ff',
        time: 13
      },{
        text: '第 15s 出现的弹幕',
        color: '#ff00ff',
        time: 15
      },{
        text: '第 17s 出现的弹幕',
        color: '#ff00ff',
        time: 17
      }]
      
  },

  bindplay:function() {
    console.log("播放");
  },
  bindpause: function () {
    console.log("暂停");
  }
  

})