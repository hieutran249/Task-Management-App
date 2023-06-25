// set the default date for the deadline input when creating new task
const defaultDate = document.querySelector('.deadline-default');
const date = new Date(Date.now())
if (defaultDate) {
    const formattedDate = date.toISOString().slice(0, 10)
    console.log(formattedDate)
    defaultDate.value = formattedDate
}
document.querySelector('#deadline').setAttribute('min', date.toISOString().slice(0, 10));