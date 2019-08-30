export function exportDate1 () {
  let data = new Date().toLocaleDateString();
  let date = data.split("/");
  if(date[1] < 10){
    date[1] = "0" + date[1];
  }
  return date[0]+date[1]+date[2];
}
