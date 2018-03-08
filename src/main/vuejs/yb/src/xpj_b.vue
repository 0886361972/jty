<style scoped>
  ul {
    list-style: none;
  }

  .link li {
    padding-left: 10px;
    padding-right: 10px;
    border-bottom: 1px solid #DEDEDE;
    height: 60px;
    line-height: 60px;
    font-size: 14px;
    color: #4A4A4A;

  }

  .link li span {
    display: inline-block;
    padding-left: 4px;
    padding-right: 4px;
    height: 24px;
    line-height: 24px;
    background: #FF9000;
    color: #fff;
    border-radius: 4px;

  }

  .planLine {
    background: url(./assets/link.png) right center no-repeat;
    -webkit-background-size: 10px auto;
    background-size: 10px auto;
    white-space: nowrap;
    text-overflow: ellipsis;
    overflow: hidden;
  }

  .detail {
    padding-left: 15px;
    padding-right: 15px;
    margin-top: 10px;
  }

  .detail h2 {
    font-size: 18px;
    font-weight: bold;
    line-height: 1.8em;
  }

  .detail h6 {
    font-size: 12px;
    color: #999;
    line-height: 2em;
  }

  .detail p {
    font-size: 14px;
    line-height: 1.6em;
  }

  .detail {

  }

  .layout {
    border: 1px solid #d7dde4;
    background: #f5f7f9;
    position: relative;
    border-radius: 4px;
    overflow: hidden;
  }

  .layout-breadcrumb {
    padding: 10px 15px 0;
  }

  .layout-content {
    min-height: 300px;
    margin: 15px;
    overflow: hidden;
    background: #fff;
    border-radius: 4px;
  }

  .layout-content-main {
    padding: 10px;
  }

  .layout-copy {
    text-align: center;
    padding: 10px 0 20px;
    background-color: #d63936;
    color: #ffffff;
  }

  .layout-menu-left {
    background: #ffffff;
  }

  .layout-header {
    height: 60px;
    background: #fff;
    box-shadow: 0 1px 1px rgba(0, 0, 0, .1);
  }

  .layout-logo-left {
    width: 90%;
    height: 30px;
    line-height: 30px;
    border-radius: 3px;
    margin: 15px auto;
    color: #fff9ec;
    background-color: #4a69f3;
    overflow: hidden;
    text-align: center;
  }

  .layout-ceiling-main a {
    color: #9ba7b5;
  }

  .layout-hide-text .layout-text {
    display: none;
  }

  .ivu-col {
    transition: width .2s ease-in-out;
  }

  .jieshuo h4 {
    background: #ef393b;
    padding: 10px 15px;
    color: #fff;
    text-align: center;
  }

  .jieshuo p {
    padding: 10px 15px;
    text-indent: 2em;
  }
</style>

<template>
  <div class="layout" :class="{'layout-hide-text': spanLeft < 5}">
    <Carousel autoplay>
      <Carousel-item v-html="headerImgs[0]"></Carousel-item>
      <Carousel-item v-html="headerImgs[1]"></Carousel-item>
      <Carousel-item v-html="headerImgs[2]"></Carousel-item>
    </Carousel>
    <Row type="flex">
      <i-col :span="spanLeft" class="layout-menu-left">
        <Menu active-name="1" width="auto">
          <div class="layout-logo-left">PK计划网</div>
          <Menu-item name="1">
            <Icon type="clock" :size="iconSize"></Icon>
            <span @click="changeLotteryType('SSC')" class="layout-text">重庆时时彩</span>
          </Menu-item>
          <Menu-item name="2">
            <Icon type="model-s" :size="iconSize"></Icon>
            <span @click="changeLotteryType('PK10')" class="layout-text">北京赛车</span>
          </Menu-item>
          <Menu-item name="3">
            <Icon type="flag" :size="iconSize"></Icon>
            <span @click="changeLotteryType('GD11X5')" class="layout-text">广东11选5</span>
          </Menu-item>
          <Menu-item name="4">
            <Icon type="speakerphone" :size="iconSize"></Icon>
            <span @click="changeLotteryType('JSK3')" class="layout-text">江苏快三</span>
          </Menu-item>
          <Menu-item name="5">
            <Icon type="help-buoy" :size="iconSize"></Icon>
            <span @click="changeLotteryType('GDKL10')" class="layout-text">广东快乐10</span>
          </Menu-item>
        </Menu>
      </i-col>
      <i-col :span="spanRight">
        <div class="layout-header">
          <i-button type="text" @click="toggleClick">
            <Icon type="navicon" size="32"></Icon>
          </i-button>
          <Select v-model="categorySelected" v-bind:placeholder="categoryPlaceholder"
                  style="width:150px">
            <Option v-for="item in categoryList" :value="item.value" :key="item">{{ item.label }}
            </Option>
          </Select>
          <Select v-model="qiSelected" placeholder="计划期数" style="width:150px">
            <Option v-for="item in qiList" :value="item.value" :key="item">{{ item.label }}
            </Option>
          </Select>
          <Select v-model="maSelected" v-bind:placeholder="maPlaceholder" style="width:150px">
            <Option v-for="item in maList" :value="item.value" :key="item">{{ item.label }}
            </Option>
          </Select>
        </div>
        <!-- <div class="layout-breadcrumb">
           <Breadcrumb>
             <Breadcrumb-item>
               <Select v-model="categorySelected" v-bind:placeholder="categoryPlaceholder"
                       style="width:150px">
                 <Option v-for="item in categoryList" :value="item.value" :key="item">{{ item.label }}
                 </Option>
               </Select>
             </Breadcrumb-item>
             <Breadcrumb-item>
               <Select v-model="qiSelected" placeholder="计划期数" style="width:150px">
                 <Option v-for="item in qiList" :value="item.value" :key="item">{{ item.label }}
                 </Option>
               </Select>
             </Breadcrumb-item>
             <Breadcrumb-item>
               <Select v-model="maSelected" v-bind:placeholder="maPlaceholder" style="width:150px">
                 <Option v-for="item in maList" :value="item.value" :key="item">{{ item.label }}
                 </Option>
               </Select>
             </Breadcrumb-item>
           </Breadcrumb>
         </div>-->
        <div class="layout-content">

          <div v-if="showDetail==false" class="layout-content-main">
            <ul class="link">
              <li class="link li" v-for="item in plantList" v-html="packPlanLineHtml(item)"
                  @click.prevent="loadPlanDetail(item.id)"></li>
            </ul>
            <Page :total="planListTotal" v-on:on-change="planPageChange"></Page>
          </div>
          <div v-else="showDetail==true">
            <Card style="width:100%;">
              <p slot="title">
                <Icon type="ios-star-outline"></Icon>
                {{lotteryType}}
              </p>
              <a href="#" slot="extra" @click.prevent="closePlantDeital">
                <Icon type="close"></Icon>
                关闭
              </a>
              <div class="detail">
                <h4>{{planDetail.title}}</h4>
                <h6>发表于 {{planDetail.createtime}}</h6>
                <div v-html="planDetail.content"></div>
              </div>
              <div class="jieshuo">
                <h4>计划解说</h4>
                <div>
                  <div style="text-align: center;">
                    <div style="font-size: 13.3333px;">
                      <div style="font-size: 13.3333px;">
                        <div style="font-size: 13.3333px;">
                          <div>
                            <div style="font-size: 13.3333px;">
                              <div style="font-size: 13.3333px;">
                                <div style="font-size: 13.3333px;">
                                <span style="color: rgb(255, 255, 255);"><span style="font-size: 18px;"><span
                                  style="background-color: rgb(255, 0, 0);">易记域名①：PKJiHua.com</span></span></span></div>
                                <div style="font-size: 13.3333px;">
                                <span style="color: rgb(255, 255, 255);"><span style="font-size: 18px;"><span
                                  style="background-color: rgb(255, 140, 0);">易记域名②：18939.com</span></span></span></div>
                                <!-- <div style="font-size: 13.3333px;">
                                 <span style="color: rgb(255, 255, 255);"><span style="font-size: 18px;"><span
                                   style="background-color: rgb(255, 215, 0);">易记域名③：56710.COM</span></span></span></div>-->
                                <div style="font-size: 13.3333px;">
                                  <span style="color: rgb(255, 0, 0); font-size: 14px;">【站长担保网投】</span><span
                                  style="font-size: 13.3333px; color: rgb(0, 0, 205);"><span style="font-size: 14px;"><a
                                  href="http://www.1379899.com">www.1379899.com</a></span></span></div>
                                <div style="font-size: 13.3333px;">
                                  <strong><span style="font-size: 14px;">一直被模仿，从未被超越的彩票网站</span></strong></div>
                                <div style="font-size: 13.3333px;">
                                  <span style="font-size: 14px;">交流群：</span><b
                                  style="color: rgb(255, 0, 0); font-size: 16px;">{{qunHao}}</b><a
                                  v-bind:href="qunUrl" style="font-size: 13.3333px;" target="_blank"><img alt="奇人神算C"
                                                                                                          border="0"
                                                                                                          src="//pub.idqqimg.com/wpa/images/group.png"
                                                                                                          title="奇人神算C"/></a><span
                                  style="font-size: 14px;">资料连准8期</span></div>
                                <div style="font-size: 13.3333px;">
                                  <div style="font-size: 13.3333px;">
                                  <span style="font-size: 14px;"><span
                                    style="color: rgb(255, 0, 0);">【诚信担保网投】</span><span
                                    style="color: rgb(0, 0, 255);"><a
                                    href="http://m.1379899.com">www.1379899.com</a></span></span>
                                  </div>
                                  <div style="font-size: 13.3333px;">
                                    <a href="http://m.1379899.com"><span
                                      style="color: rgb(51, 51, 51); font-family: 'Microsoft YaHei', sans-serif; font-size: 13.3333px; font-weight: bold; line-height: 24px; orphans: 2; widows: 2;">站长推荐，绝对错不了</span></a>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div v-html="planDetail.planDesc"></div>
              </div>
            </Card>
          </div>

        </div>
        <div class="layout-copy">
          <p>本网站赛程、赛果等数据由国家彩票管理总局中心发布 www.pkjihua.com
            <br/>
            友情提示：购彩有风险，买彩要适度未成年人禁止购买彩票</p>
          <div style="font-size: 13.3333px;">
            <span style="font-size: 14px;">计划交流群：</span><b
            style="font-size: 16px;">{{qunHao}}</b><a
            v-bind:href="qunUrl" style="font-size: 13.3333px;" target="_blank"><img alt="奇人神算C" border="0"
                                                                                    src="//pub.idqqimg.com/wpa/images/group.png"
                                                                                    title="奇人神算C"/></a></div>
        </div>
      </i-col>
    </Row>
  </div>
</template>
<script>
  var planLineBackImage = require('./assets/link.png');
  var headerImg01 = require('./assets/header01.jpg');
  var headerImg02 = require('./assets/header02.jpg');
  var headerImg03 = require('./assets/header03.jpg');

  export default {
    data () {
      return {
        qunUrl: "",
        qunHao: "",


        showDetail: false,
        planDetail: {"title": '', "content": '', 'createtime': ''},
        planListTotal: 0,
        planCurrentPage: 1,
        spanLeft: 5,
        spanRight: 19,
        lotteryType: '重庆时时彩',
        categoryPlaceholder: '计划球位',
        maPlaceholder: '计划码数',
        maList: [],
        qiList: [],
        categoryList: [],
        categoryUrl: "ssc",
        categorySelected: '',
        qiSelected: '',
        maSelected: '',
        plantList: [],
        headerImgs: ['<a href="http://www.1379899.com" ><img src="' + headerImg01 + '" height="150" width="100%"></a>',
          '<a href="http://www.1379899.com" ><img src="' + headerImg02 + '" height="150" width="100%"></a>',
          '<a href="http://www.1379899.com" ><img src="' + headerImg03 + '" height="150" width="100%"></a>']
      }
    },
    computed: {
      iconSize () {
        return this.spanLeft === 5 ? 14 : 24;
      }
    }, created(){
      this.setList();
      this.loadPlanLine();
    },
    watch: {
      categorySelected: function (newValue) {
        this.loadPlanLine();
      },
      qiSelected: function (newValue) {
        this.loadPlanLine();
      },
      maSelected: function (newValue) {
        this.loadPlanLine();
      }
    },
    methods: {
      planPageChange(v){
        this.planCurrentPage = v;
        this.loadPlanLine();
      },
      loadPlanLine(){
        var self = this;
        var url = "plan/article/" + this.categoryUrl + "/json.front?rnd=" + Math.random() + "&filter_EQS_categoryType=" + self.categorySelected + "&filter_EQS_qi=" + self.qiSelected + "&filter_EQS_ma=" + self.maSelected;
        if (this.planCurrentPage > 1) {
          url = url + "&page=" + this.planCurrentPage;
        }
        this.axios.post(url)
          .then(function (res) {
            var data = res.data;
            if (data) {
              self.planListTotal = data.total;
              var list = [];
              var rows = data.rows;
              for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                list.push({'id': row.id, "categoryDesc": row.categoryDesc, "title": row.title})
              }
            }
            self.plantList = list;
          })
          .catch(function (error) {
          });
      },
      closePlantDeital(){
        this.showDetail = false;
      },
      loadPlanDetail(id){
        var self = this;
        var url = "plan/article/show/" + id + ".front";
        this.axios.post(url, {})
          .then(function (res) {
            self.showDetail = true;
            self.planDetail = res.data;
            self.qunUrl = self.planDetail.qunUrl;
            self.qunHao = self.planDetail.qunHao;
          })
          .catch(function (error) {
          });
      },
      packPlanLineHtml(item){
        return '<a href="">' +
          '<p style="background:url(' + planLineBackImage + ') right center no-repeat;background-size: 10px auto;white-space: nowrap;text-overflow: ellipsis;overflow: hidden;">' +
          '<span style="display:inline-block;padding-left: 4px;padding-right: 4px;height: 24px;line-height: 24px;background: #FF9000;color: #fff;border-radius: 4px;">' +
          item.categoryDesc +
          '</span>  ' + item.title + '</p></a>';
      },
      toggleClick () {
        if (this.spanLeft === 5) {
          this.spanLeft = 2;
          this.spanRight = 22;
        } else {
          this.spanLeft = 5;
          this.spanRight = 19;
        }
      },
      changeLotteryType(val){
        this.planCurrentPage = 1;
        this.categorySelected = '';
        this.qiSelected = '';
        this.maSelected = '';
        this.showDetail = false;
        if (val == 'SSC') {
          this.lotteryType = '重庆时时彩';
          this.categoryPlaceholder = '计划球位';
          this.maPlaceholder = '计划码数';
          this.categoryUrl = "ssc";
        } else if (val == 'PK10') {
          this.lotteryType = '北京赛车';
          this.categoryPlaceholder = '计划球位';
          this.maPlaceholder = '计划码数';
          this.categoryUrl = "pk10";
        } else if (val == 'GD11X5') {
          this.lotteryType = '广东11选5';
          this.categoryPlaceholder = '计划球位';
          this.maPlaceholder = '计划码数';
          this.categoryUrl = "gd11x5";
        } else if (val == 'JSK3') {
          this.lotteryType = '江苏快3';
          this.categoryPlaceholder = '计划球位';
          this.maPlaceholder = '计划组数';
          this.categoryUrl = "jsk3";
        } else if (val == 'GDKL10') {
          this.lotteryType = '广东快乐10分';
          this.categoryPlaceholder = '计划球位';
          this.maPlaceholder = '计划码数';
          this.categoryUrl = "gdkl10";
        }
        this.setList();
        this.loadPlanLine();
      },
      setList(){
        if (this.lotteryType == '重庆时时彩') {
          this.maList = [
            {
              value: '',
              label: '全部'
            },
            {
              value: '1码',
              label: '一码'
            },
            {
              value: '2码',
              label: '二码'
            },
            {
              value: '3码',
              label: '三码'
            },
            {
              value: '4码',
              label: '四码'
            },
            {
              value: '5码',
              label: '五码'
            },
            {
              value: '6码',
              label: '六码'
            },
            {
              value: '7码',
              label: '七码'
            }
          ];
          this.qiList = [
            {
              value: '',
              label: '全部'
            },
            {
              value: '一期',
              label: '一期'
            },
            {
              value: '二期',
              label: '二期'
            },
            {
              value: '三期',
              label: '三期'
            }];
          this.categoryList = [
            {
              value: '',
              label: '全部'
            },
            {
              value: 'N1',
              label: '个位'
            },
            {
              value: 'N2',
              label: '十位'
            },
            {
              value: 'N3',
              label: '百位'
            },
            {
              value: 'N4',
              label: '千位'
            },
            {
              value: 'N5',
              label: '万位'
            },
            {
              value: 'A',
              label: '五星'
            }
          ]
        }
        else if (this.lotteryType == '北京赛车') {
          this.maList = [
            {
              value: '',
              label: '全部'
            },
            {
              value: '1码',
              label: '一码'
            },
            {
              value: '2码',
              label: '二码'
            },
            {
              value: '3码',
              label: '三码'
            },
            {
              value: '4码',
              label: '四码'
            },
            {
              value: '5码',
              label: '五码'
            },
            {
              value: '6码',
              label: '六码'
            },
            {
              value: '7码',
              label: '七码'
            }];
          this.qiList = [
            {
              value: '',
              label: '全部'
            },
            {
              value: '一期',
              label: '一期'
            },
            {
              value: '二期',
              label: '二期'
            },
            {
              value: '三期',
              label: '三期'
            }];
          this.categoryList = [
            {
              value: '',
              label: '全部'
            },
            {
              value: 'N1',
              label: '冠军'
            },
            {
              value: 'N2',
              label: '亚军'
            },
            {
              value: 'N3',
              label: '第三名'
            },
            {
              value: 'N4',
              label: '第四名'
            },
            {
              value: 'N5',
              label: '第五名'
            },
            {
              value: 'N6',
              label: '第六名'
            },
            {
              value: 'N7',
              label: '第七名'
            },
            {
              value: 'N8',
              label: '第八名'
            },
            {
              value: 'N9',
              label: '第九名'
            },
            {
              value: 'N10',
              label: '第十名'
            }
          ];

        }
        else if (this.lotteryType == '广东11选5') {
          this.maList = [
            {
              value: '',
              label: '全部'
            },
            {
              value: '1码',
              label: '一码'
            },
            {
              value: '2码',
              label: '二码'
            },
            {
              value: '3码',
              label: '三码'
            },
            {
              value: '4码',
              label: '四码'
            },
            {
              value: '5码',
              label: '五码'
            },
            {
              value: '6码',
              label: '六码'
            },
            {
              value: '7码',
              label: '七码'
            }];
          this.qiList = [
            {
              value: '',
              label: '全部'
            },
            {
              value: '一期',
              label: '一期'
            },
            {
              value: '二期',
              label: '二期'
            },
            {
              value: '三期',
              label: '三期'
            }];
          this.categoryList = [
            {
              value: '',
              label: '全部'
            },
            {
              value: 'N1',
              label: '第一球'
            },
            {
              value: 'N2',
              label: '第二球'
            },
            {
              value: 'N3',
              label: '第三球'
            },
            {
              value: 'N4',
              label: '第四球'
            },
            {
              value: 'N5',
              label: '第五球'
            },
            {
              value: 'N1-1',
              label: '一中一'
            },
            {
              value: 'N2-2',
              label: '二中二'
            }];

        } else if (this.lotteryType == '江苏快3') {
          this.maList = [
            {
              value: '',
              label: '全部'
            },
            {
              value: '1组',
              label: '一组'
            },
            {
              value: '2组',
              label: '二组'
            },
            {
              value: '4组',
              label: '四组'
            }
            ,
            {
              value: '6组',
              label: '六组'
            }];
          this.qiList = [
            {
              value: '',
              label: '全部'
            },
            {
              value: '一期',
              label: '一期'
            },
            {
              value: '二期',
              label: '二期'
            },
            {
              value: '三期',
              label: '三期'
            }];
          this.categoryList = [
            {
              value: '',
              label: '全部'
            },
            {
              value: 'N1',
              label: '独胆'
            },
            {
              value: 'N2',
              label: '两连'
            }];

        } else if (this.lotteryType == '广东快乐10分') {
          this.maList = [
            {
              value: '',
              label: '全部'
            },
            {
              value: '1码',
              label: '一码'
            },
            {
              value: '2码',
              label: '二码'
            },
            {
              value: '4码',
              label: '四码'
            },
            {
              value: '6码',
              label: '六码'
            },
            {
              value: '8码',
              label: '八码'
            },
            {
              value: '10码',
              label: '十码'
            },
            {
              value: '12码',
              label: '十二码'
            }];
          this.qiList = [
            {
              value: '',
              label: '全部'
            },
            {
              value: '一期',
              label: '一期'
            },
            {
              value: '二期',
              label: '二期'
            },
            {
              value: '三期',
              label: '三期'
            }];
          this.categoryList = [
            {
              value: '',
              label: '全部'
            },
            {
              value: 'N1',
              label: '第一球'
            },
            {
              value: 'N2',
              label: '第二球'
            },
            {
              value: 'N3',
              label: '第三球'
            },
            {
              value: 'N4',
              label: '第四球'
            },
            {
              value: 'N5',
              label: '第五球'
            }, {
              value: 'N6',
              label: '第六球'
            }, {
              value: 'N7',
              label: '第七球'
            }, {
              value: 'N8',
              label: '第八球'
            }, {
              value: 'N2-2',
              label: '二中二'
            }];

        }
      },
      setCategoryList(){

      },
      setQiList(){

      }
    }
  }
</script>
