// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#858796';

function number_format(number, decimals, dec_point, thousands_sep) {
  // *     example: number_format(1234.56, 2, ',', ' ');
  // *     return: '1 234,56'
  number = (number + '').replace(',', '').replace(' ', '');
  var n = !isFinite(+number) ? 0 : +number,
    prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
    sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
    dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
    s = '',
    toFixedFix = function(n, prec) {
      var k = Math.pow(10, prec);
      return '' + Math.round(n * k) / k;
    };
  // Fix for IE parseFloat(0.55).toFixed(0) = 0;
  s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
  if (s[0].length > 3) {
    s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
  }
  if ((s[1] || '').length < prec) {
    s[1] = s[1] || '';
    s[1] += new Array(prec - s[1].length + 1).join('0');
  }
  return s.join(dec);
}

const url = "https://communityoneapi.herokuapp.com/projects";

let price;
let binance_price = new Array();
let kimchi_price = new Array();
let month;


window.onload=function(){
  fetch("https://crix-api-cdn.upbit.com/v1/crix/candles/months?code=CRIX.UPBIT.USDT-BTC&count=12&ciqrandom=1644651039982%EF%BB%BF")
      .then((response) => response.json())
      .then(function(data){
        price = data.map((item) => parseInt(item.tradePrice));
        month = data.map((item) => item.firstDayOfPeriod.substr(0,7));})
      .then(function(){
        fetch('https://www.binance.com/api/v3/klines?symbol=BTCUSDT&interval=1M')
            .then((response) => response.json())
            .then(function(data){
              let closePrice = data.filter((item,index) => {
                if(data.length-index <= 12) {
                  binance_price.push(parseInt(item[4]))
                  return parseInt(item[1])
                }
              });
            })
            .then(function (){
              for(let i= 0; i<price.length; i++)
              {
                //console.log('price: '+ price[price.length - i- 1])
                //console.log('binance: '+ binance_price[i])
                //kimchi_price.push((price[price.length - i- 1] - binance_price[i])/binance_price[i]*100)
                kimchi_price.push(parseInt((price[price.length - i- 1] - binance_price[i])));
              }

            })
            .then(
                function () {
                  console.log('kimchi: '+kimchi_price);
                  console.log('price: '+price);
                  console.log('binance: '+binance_price);
                }
            ).then(function () {
          // Area Chart Example
          var ctx = document.getElementById("myAreaChart");
          var myLineChart = new Chart(ctx, {
            type: 'line',
            data: {
              labels: month.reverse(),
              datasets: [{
                label: "Earnings",
                lineTension: 0.3,
                backgroundColor: "rgba(78, 115, 223, 0.05)",
                borderColor: "rgba(51, 174, 192, 1)",
                pointRadius: 3,
                pointBackgroundColor: "rgba(51, 174, 192, 1)",
                pointBorderColor: "rgba(51, 174, 192, 1)",
                pointHoverRadius: 3,
                pointHoverBackgroundColor: "rgba(51, 174, 192, 1)",
                pointHoverBorderColor: "rgba(51, 174, 192, 1)",
                pointHitRadius: 10,
                pointBorderWidth: 2,
                //data: [60, -1693, 111, 754, 123, 38, 350, -51, -33, 63, -32, 10],
                //data: price.reverse(),
                data: kimchi_price
              }],
            },
            options: {
              maintainAspectRatio: false,
              layout: {
                padding: {
                  left: 10,
                  right: 25,
                  top: 25,
                  bottom: 0
                }
              },
              scales: {
                xAxes: [{
                  time: {
                    unit: 'date'
                  },
                  gridLines: {
                    display: false,
                    drawBorder: false
                  },
                  ticks: {
                    maxTicksLimit: 7
                  }
                }],
                yAxes: [{
                  ticks: {
                    maxTicksLimit: 5,
                    padding: 10,
                    // Include a dollar sign in the ticks
                    callback: function(value, index, values) {
                      return '$' + number_format(value);
                    }
                  },
                  gridLines: {
                    color: "rgb(234, 236, 244)",
                    zeroLineColor: "rgb(234, 236, 244)",
                    drawBorder: false,
                    borderDash: [2],
                    zeroLineBorderDash: [2]
                  }
                }],
              },
              legend: {
                display: false
              },
              tooltips: {
                backgroundColor: "rgb(255,255,255)",
                bodyFontColor: "#858796",
                titleMarginBottom: 10,
                titleFontColor: '#6e707e',
                titleFontSize: 14,
                borderColor: '#dddfeb',
                borderWidth: 1,
                xPadding: 15,
                yPadding: 15,
                displayColors: false,
                intersect: false,
                mode: 'index',
                caretPadding: 10,
                callbacks: {
                  label: function(tooltipItem, chart) {
                    var datasetLabel = chart.datasets[tooltipItem.datasetIndex].label || '';
                    return datasetLabel + ': $' + number_format(tooltipItem.yLabel);
                  }
                }
              }
            }
          });
        })
            .then(function () {
              console.log(price[0].toString())
              console.log(binance_price[binance_price.length-1].toString())
              let upbit_btc = price[0]
              let binance_btc = binance_price[binance_price.length-1]
              let upbit_binance_btc = price[0] - binance_price[binance_price.length-1]
              document.getElementById('upbit_btc').innerHTML = upbit_btc.toString() + '$'
              document.getElementById('binance_btc').innerHTML = binance_btc.toString() + '$'
              document.getElementById('upbit_binance_btc').innerHTML = upbit_binance_btc.toString() + '$'
            });
      });
}

/*
// Area Chart Example
var ctx = document.getElementById("myAreaChart");
var myLineChart = new Chart(ctx, {
  type: 'line',
  data: {
    labels: month.reverse(),
    datasets: [{
      label: "Earnings",
      lineTension: 0.3,
      backgroundColor: "rgba(78, 115, 223, 0.05)",
      borderColor: "rgba(78, 115, 223, 1)",
      pointRadius: 3,
      pointBackgroundColor: "rgba(78, 115, 223, 1)",
      pointBorderColor: "rgba(78, 115, 223, 1)",
      pointHoverRadius: 3,
      pointHoverBackgroundColor: "rgba(78, 115, 223, 1)",
      pointHoverBorderColor: "rgba(78, 115, 223, 1)",
      pointHitRadius: 10,
      pointBorderWidth: 2,
      //data: [60, -1693, 111, 754, 123, 38, 350, -51, -33, 63, -32, 10],
      //data: price.reverse(),
      data: kimchi_price
    }],
  },
  options: {
    maintainAspectRatio: false,
    layout: {
      padding: {
        left: 10,
        right: 25,
        top: 25,
        bottom: 0
      }
    },
    scales: {
      xAxes: [{
        time: {
          unit: 'date'
        },
        gridLines: {
          display: false,
          drawBorder: false
        },
        ticks: {
          maxTicksLimit: 7
        }
      }],
      yAxes: [{
        ticks: {
          maxTicksLimit: 5,
          padding: 10,
          // Include a dollar sign in the ticks
          callback: function(value, index, values) {
            return '$' + number_format(value);
          }
        },
        gridLines: {
          color: "rgb(234, 236, 244)",
          zeroLineColor: "rgb(234, 236, 244)",
          drawBorder: false,
          borderDash: [2],
          zeroLineBorderDash: [2]
        }
      }],
    },
    legend: {
      display: false
    },
    tooltips: {
      backgroundColor: "rgb(255,255,255)",
      bodyFontColor: "#858796",
      titleMarginBottom: 10,
      titleFontColor: '#6e707e',
      titleFontSize: 14,
      borderColor: '#dddfeb',
      borderWidth: 1,
      xPadding: 15,
      yPadding: 15,
      displayColors: false,
      intersect: false,
      mode: 'index',
      caretPadding: 10,
      callbacks: {
        label: function(tooltipItem, chart) {
          var datasetLabel = chart.datasets[tooltipItem.datasetIndex].label || '';
          return datasetLabel + ': $' + number_format(tooltipItem.yLabel);
        }
      }
    }
  }
});*/
