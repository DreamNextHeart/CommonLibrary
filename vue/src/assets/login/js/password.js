export function getRandomPassword (len) {
  len = len || 32
  var $chars1 = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz' /** **默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1****/
  var $chars2='2345678!,.?'
  var maxPos1 = $chars1.length
  var maxPos2 = $chars2.length
  var pwd = ''
  for (let i = 0; i < len/2; i++) {
    pwd += $chars1.charAt(Math.floor(Math.random() * maxPos1))
  }
  for (let i = 0; i < len/2; i++) {
    pwd += $chars2.charAt(Math.floor(Math.random() * maxPos2))
  }
  return pwd
}
