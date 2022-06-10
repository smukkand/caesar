//<Author: smukkand@github.com >

// (function () {
//   var query = {'_queryFilter': 'true'};
//   logger.info("source name Data are : " + source.__NAME__);
//   var mem = source.__NAME__.substring(source.__NAME__.indexOf('/') +1 );
//   logger.info("output ID for " + source.__NAME__ + " is: " + mem);

// //   for (i=0;i<source.__NAME__.length;i++) {
// //     logger.info("found target: " + source.__NAME__[i]);
// //     var mem = source.__NAME__[i].substring(source.__NAME__[i].indexOf('/') +1 );
// //     return mem;
// // }
// } ());


// (function () {

//   var query = {'_queryId': 'query-all-ids'};
//   var resultData = openidm.query("system/GoogleIdentity/__GROUP__", query);
//   logger.info("foundData " + resultData.result.length)
//   for (i=0;i<resultData.result.length;i++) {
//     logger.info("foundtarget: " + resultData.result[i]._id);
//     return resultData.result[i]._id;
//   };
//   // logger.info("finaltarget: " + id);
//   // return id;
// } ());


(function () {
  var array = [];
  var query = {'_queryFilter': 'true'};
  var resultData = openidm.query("/system/GojekGoogleProd/Member", query);
  // const jsonData = JSON.parse(resultData);

  for (i=0;i<resultData.result.length;i++) {
    var currentKey = resultData.result[i];
    logger.info("CurrentKey Value is : " + currentKey);

    if (currentKey.hasOwnProperty('__NAME__')) {

      logger.info("Name exist");
      var gid = currentKey.__NAME__.substring(0, currentKey.__NAME__.indexOf('/'));
      // logger.info("GID value is: " + gid);
      logger.info("sourceID is: " + source._id + " and GID value is: " + gid);
      if (gid === source._id){
        logger.info("test Works");
        var member = currentKey.__NAME__.substring(currentKey.__NAME__.indexOf('/') +1 )
        logger.info("member is: " + member );
        array.push(member);
        // return member;
      }
    }  
    else {
      logger.info("Not foundKey");
    }
    }
    logger.info("array is: " + array );
    return array

  // logger.info("foundData " + jsonData.result.length + " : data, and the outputs are:" + jsonData.result[0]);

  // var result = jsonData.result.map(function(a) {
  //   logger.info("foundtarget: " + a._id);
  //   return a._id;});


  // var output = result;
  // logger.info("Outputs are : " + output);
  // for (i=0;i<output.length;i++) {
  // return output[i];
  // logger.info("Final data is: " + output[i]);
  // }
} ());


// script/googleMap/MapgroupID.js

