export function transTimestamp(timestamp) {
  // 创建一个Date对象，传入毫秒时间戳
  const date = new Date(timestamp);

  // 获取年、月、日、时、分、秒
  const year = date.getFullYear(); // 获取年份
  const month = date.getMonth() + 1; // 获取月份，注意月份是从0开始的
  const day = date.getDate(); // 获取日期
  const hours = date.getHours(); // 获取小时
  const minutes = date.getMinutes(); // 获取分钟
  const seconds = date.getSeconds(); // 获取秒

  // 将月、日、时、分、秒补零，确保它们是两位数
  const formattedMonth = month.toString().padStart(2, '0');
  const formattedDay = day.toString().padStart(2, '0');
  const formattedHours = hours.toString().padStart(2, '0');
  const formattedMinutes = minutes.toString().padStart(2, '0');
  const formattedSeconds = seconds.toString().padStart(2, '0');

  // 拼接成“年月日时分秒”的格式
  const result = `${year}-${formattedMonth}-${formattedDay} ${formattedHours}:${formattedMinutes}:${formattedSeconds}`;

  return result;
}