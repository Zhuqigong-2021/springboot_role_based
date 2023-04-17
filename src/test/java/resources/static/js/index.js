
const btnShow = document.getElementById("show");
const btnEye = document.getElementById("eye");
const docCard = document.getElementById("docCard");
const docTable = document.getElementById("docTable");
const nurseCard = document.getElementById("nurseCard");
const nurseTable = document.getElementById("nurseTable");
const nurseSortBar = document.getElementById("nurseSort");


btnShow.addEventListener("click",showTable)
function showTable(){
 docCard.classList.toggle("hidden");
 docTable.classList.toggle("hidden");
}

btnEye.addEventListener("click" , changeVisualization)
function changeVisualization(){
 nurseCard.classList.toggle("hidden");
 nurseTable.classList.toggle("hidden");
 nurseSortBar.classList.toggle("hidden");
}


//ascending patient sort
let rowsPatients = document.querySelectorAll("tr[data-patient]");
rowsPatients = Array.prototype.slice.call(rowsPatients, 0);

function sortPatientAsc(){


 rowsPatients.sort(function(a, b) {
  let nameA = a.getAttribute('data-patient').toUpperCase();
  let nameB = b.getAttribute('data-patient').toUpperCase();
  if (nameA < nameB) return -1;
  if (nameA > nameB) return 1;
  return 0;
 });
 let tbody = document.querySelector('.patient_tbody');
 rowsPatients.forEach(function(row) {
  tbody.appendChild(row);
 });
}

//descending patient sort
function sortPatientDesc(){


 rowsPatients.sort(function(a, b) {
  let nameA = a.getAttribute('data-patient').toUpperCase();
  let nameB = b.getAttribute('data-patient').toUpperCase();
  if (nameA < nameB) return 1;
  if (nameA > nameB) return -1;
  return 0;
 });
 let tbody = document.querySelector('.patient_tbody');
 rowsPatients.forEach(function(row) {
  tbody.appendChild(row);
 });
}



function searchPatient(){

  const patient_input = document.getElementById("search_patient");
 const filter = patient_input.value.toUpperCase();

  // const  table = document.getElementsByTagName("table")[2];
 const  table = document.getElementById("patient_table");

  const rows = table.getElementsByTagName("tr");

 for (let i = 1; i < rows.length; i++) {

   let name = rows[i].getAttribute("data-patient-all")?.toUpperCase().trim();

    // name.includes(filter)? console.log("yes"):console.log("no")
  if (name.indexOf(filter) > -1) {
   rows[i].style.display = "";

  } else {

   rows[i].style.display = "none";
  }
 }
}


//nurses sort
let rowsNurses = document.querySelectorAll('tr[data-nurse]');
rowsNurses = Array.prototype.slice.call(rowsNurses, 0);


function sortNurseAsc(){


 rowsNurses.sort(function(a, b) {
  let nameA = a.getAttribute('data-nurse').toUpperCase();
  let nameB = b.getAttribute('data-nurse').toUpperCase();
  if (nameA < nameB) return -1;
  if (nameA > nameB) return 1;
  return 0;
 });
 let tbody = document.querySelector('.nurse_tbody');
 rowsNurses.forEach(function(row) {
  tbody.appendChild(row);
 });
}

//descending nurse sort
function sortNurseDesc(){


 rowsNurses.sort(function(a, b) {
  let nameA = a.getAttribute('data-nurse').toUpperCase();
  let nameB = b.getAttribute('data-nurse').toUpperCase();
  if (nameA < nameB) return 1;
  if (nameA > nameB) return -1;
  return 0;
 });
 let tbody = document.querySelector('.nurse_tbody');
 rowsNurses.forEach(function(row) {
  tbody.appendChild(row);
 });
}

function searchNurse(){

 const nurse_input = document.getElementById("search_nurse");
 const filter = nurse_input.value.toUpperCase().trim();


 const  table = document.getElementById("nurseTable");

 const rows = table.getElementsByTagName("tr");

 for (let i = 1; i < rows.length; i++) {

  let name = rows[i].getAttribute("data-nurse-all")?.toUpperCase();
console.log(name);
  // name.includes(filter)? console.log("yes"):console.log("no")
  if (name.indexOf(filter) > -1) {
   rows[i].style.display = "";

  } else {

   rows[i].style.display = "none";
  }
 }
}

//doctor sort
let rowsDoctors = document.querySelectorAll('tr[data-doctor]');
rowsDoctors = Array.prototype.slice.call(rowsDoctors, 0);



function sortDoctorAsc(){


 rowsDoctors.sort(function(a, b) {
  let nameA = a.getAttribute('data-doctor').toUpperCase();
  let nameB = b.getAttribute('data-doctor').toUpperCase();
  if (nameA < nameB) return -1;
  if (nameA > nameB) return 1;
  return 0;
 });
 let tbody = document.querySelector('.doc_tbody');
 rowsDoctors.forEach(function(row) {
  tbody.appendChild(row);
 });
}

function sortDoctorDesc(){


 rowsDoctors.sort(function(a, b) {
  let nameA = a.getAttribute('data-doctor').toUpperCase();
  let nameB = b.getAttribute('data-doctor').toUpperCase();
  if (nameA < nameB) return 1;
  if (nameA > nameB) return -1;
  return 0;
 });
 let tbody = document.querySelector('.doc_tbody');
 rowsDoctors.forEach(function(row) {
  tbody.appendChild(row);
 });
}


function searchDoctor(){

 const doc_input = document.getElementById("search_doctor");
 const filter = doc_input.value.toUpperCase().trim();


 const  doctor_table = document.getElementById("doctor_table");

 const rows = doctor_table.getElementsByTagName("tr");

 for (let i = 1; i < rows.length; i++) {

  let name = rows[i].getAttribute("data-doctor-all")?.toUpperCase();

  if (name.indexOf(filter) > -1) {
   rows[i].style.display = "";

  } else {

   rows[i].style.display = "none";
  }
 }
}