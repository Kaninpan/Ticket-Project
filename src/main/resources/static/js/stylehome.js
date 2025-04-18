// / ฟังก์ชันเปิด/ปิด Sidebar
document.addEventListener('DOMContentLoaded', function () {
    document.querySelector('.toggle-sidebar-btn').addEventListener('click', function () {
        document.querySelector('.sidebar').classList.toggle('show');
    });
});
// =========================================================================================
// Alert Edit Profile success
setTimeout(function () {
    var alertElement = document.getElementById('successAlert');
    if (alertElement) {
        alertElement.classList.remove('show');
        alertElement.classList.add('fade');
        setTimeout(function () {
            alertElement.style.display = 'none';
        }, 150); // เวลาที่ใช้ในการทำให้จางลง
    }
}, 2300);

// =========================================================================================

// Alert Exit
document.addEventListener("DOMContentLoaded", function () {
    document.getElementById('logoutButton').onclick = function () {
        document.getElementById('logoutAlert').classList.remove('d-none');
    };

    document.getElementById('cancelButton').onclick = function () {
        document.getElementById('logoutAlert').classList.add('d-none');
    };

    document.getElementById('confirmButton').onclick = function () {
        document.getElementById('logoutForm').submit();
    };
});
// =========================================================================================
// Username and editprofile
function validateUsername(input) {
    const minLength = 5;
    if (input.value.length < minLength) {
        input.setCustomValidity(`Username ต้องมีความยาวอย่างน้อย ${minLength} ตัวอักษร`);
    } else {
        input.setCustomValidity(''); // รีเซ็ตข้อความเมื่อมีความยาวถูกต้อง
    }
}

let formElement;
const modalMessage = "คุณต้องการที่จะแก้ไขข้อมูลส่วนตัว <br> โดยที่ไม่ต้องการแก้ไขรหัสผ่านใช่หรือไม่ ? <br><br> หากแก้ไขทั้ง 2 ส่วนหรือแก้ไขเพียงส่วนเดียวสามารถกดที่ปุ่ม <b>ตกลง</b>";

function showConfirmModal(event) {
    event.preventDefault();
    formElement = event.target.form;
    // Use innerHTML to interpret the <br> tag as a line break
    document.getElementById('confirmModalBody').innerHTML = modalMessage;
    const modal = new bootstrap.Modal(document.getElementById('confirmModal'));
    modal.show(); // Show the modal
}

function confirmEdit() {
    // Submit the form
    formElement.submit();
}

// =========================================================================================
// เบอร์โทร
function formatPhone(input) {
    let value = input.value.replace(/\D/g, ''); // ลบตัวเลขที่ไม่ใช่ตัวเลข
    if (value.length > 10) {
        value = value.slice(0, 10); // เก็บแค่ 10 ตัวแรก
    }
    if (value.length > 3) {
        value = value.replace(/^(\d{3})(\d)/, '$1-$2'); // ใส่ขีดหลังเลข 3 ตัวแรก
    }
    if (value.length > 6) {
        value = value.replace(/-(\d{3})(\d)/, '-$1-$2'); // ใส่ขีดหลังเลข 3 ตัว
    }
    input.value = value; // กำหนดค่าให้กับฟิลด์
}

// =========================================================================================
function openPrivacyPolicy() {
    // เปิดหน้าต่าง popup
    var myWindow = window.open("", "", "width=900,height=900");

    // เขียนเนื้อหา HTML ลงในหน้าต่างใหม่
    myWindow.document.write(`
    <!DOCTYPE html>
    <html lang="th">
    <head>
      <meta charset="UTF-8">
      <title>นโยบายความเป็นส่วนตัว</title>
      <link href="https://fonts.googleapis.com/css2?family=Prompt&display=swap" rel="stylesheet">
      <style>
        body {
          font-family: "Prompt", sans-serif;
          font-weight: 400;
          font-style: normal;
          line-height: 1.6;
          margin: 20px;
        }
        p, ul {
          font-size: 16px;
          color: #333;
          margin-top: 20px;
        }
        ul {
          padding-left: 40px; /* เพิ่มค่าเยื้องเข้าไปมากขึ้น */
          margin-top: 10px;
        }
        li {
          margin-bottom: 10px;
        }
        h2 {
          font-size: 24px;
          margin-bottom: 20px;
        }
      </style>
    </head>
    <body>
      <h2>นโยบายความเป็นส่วนตัว</h2>

      <p>
        เรามีความมุ่งมั่นที่จะคุ้มครองข้อมูลส่วนบุคคลของคุณอย่างเต็มที่ นโยบายความเป็นส่วนตัวนี้อธิบายถึงวิธีการที่เรารวบรวม, 
        ใช้, และปกป้องข้อมูลส่วนบุคคลของคุณเมื่อคุณลงทะเบียนหรือใช้บริการของเรา
      </p>
      
      <p>
        1. ข้อมูลที่เรารวบรวม: เรารวบรวมข้อมูลส่วนบุคคลต่อไปนี้จากคุณ
      </p>
      <ul>
        <li>ชื่อและนามสกุล: ใช้เพื่อระบุตัวตนของคุณและให้บริการที่มีประสิทธิภาพ</li>
        <li>อีเมล: ใช้เพื่อการติดต่อกับคุณ, ส่งข้อมูลเกี่ยวกับบัญชีของคุณและการอัปเดตต่างๆ</li>
        <li>เบอร์โทร: ใช้เพื่อการติดต่อกรณีที่จำเป็นและตรวจสอบความถูกต้องของข้อมูล</li>
      </ul>

      <p>
        2. วิธีการใช้ข้อมูล: ข้อมูลส่วนบุคคลที่เรารวบรวมจะถูกใช้เพื่อ
      </p>
      <ul>
        <li>การจัดการบัญชีผู้ใช้ของคุณและให้บริการตามที่คุณร้องขอ</li>
        <li>การติดต่อคุณเกี่ยวกับการอัปเดตหรือการเปลี่ยนแปลงที่เกี่ยวข้องกับบัญชีของคุณ</li>
        <li>การปรับปรุงบริการของเราให้ดีขึ้นตามข้อเสนอแนะของคุณ</li>
      </ul>

      <p>
        3. การเก็บรักษาข้อมูล: เราจะเก็บข้อมูลส่วนบุคคลของคุณตราบเท่าที่จำเป็นในการให้บริการและตามข้อกำหนดทางกฎหมายที่เกี่ยวข้อง 
        เมื่อข้อมูลไม่จำเป็นอีกต่อไป เราจะทำการลบหรือทำลายข้อมูลดังกล่าวตามมาตรฐานการปกป้องข้อมูลที่เหมาะสม
      </p>

      <p>
        4. การปกป้องข้อมูล: เรามีมาตรการรักษาความปลอดภัยที่เหมาะสมในการปกป้องข้อมูลส่วนบุคคลของคุณจากการเข้าถึงโดยไม่ได้รับอนุญาต, 
        การสูญหาย, การทำลาย, หรือการรั่วไหล
      </p>

      <p>
        5. การแบ่งปันข้อมูล: เราจะไม่แบ่งปันข้อมูลส่วนบุคคลของคุณกับบุคคลที่สามโดยไม่ได้รับความยินยอมจากคุณ 
        ยกเว้นในกรณีที่จำเป็นต้องปฏิบัติตามข้อกำหนดทางกฎหมายหรือการปฏิบัติตามข้อกำหนดของบริการ
      </p>

      <p>
        6. สิทธิ์ของคุณ: คุณมีสิทธิ์ในการเข้าถึง, แก้ไข, หรือขอลบข้อมูลส่วนบุคคลของคุณที่เรามีอยู่ 
        หากคุณต้องการทำเช่นนี้, กรุณาติดต่อเราผ่านทางข้อมูลติดต่อด้านล่าง
      </p>

      <p>
        7. การเปลี่ยนแปลงนโยบาย: เราขอสงวนสิทธิ์ในการปรับปรุงนโยบายความเป็นส่วนตัวนี้เมื่อจำเป็น 
        การเปลี่ยนแปลงจะมีผลทันทีเมื่อเผยแพร่บนเว็บไซต์ของเรา เราขอแนะนำให้คุณตรวจสอบนโยบายนี้เป็นระยะๆ
      </p>

      <script>
        // ป้องกัน F5 และ Ctrl+R
        document.addEventListener('keydown', function(event) {
            if ((event.key === 'F5') || (event.ctrlKey && event.key === 'r')) {
                event.preventDefault();
                alert('การรีเฟรชหรือโหลดใหม่ถูกปิดใช้งาน');
            }
        });
      </script>
    </body>
    </html>
    `);
}

// =====================================================================================================
// ปฏิทินประจำวัน
function updateTime() {
    const options = {
        timeZone: 'Asia/Bangkok',
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit',
        hour12: false
    };

    const now = new Date();
    const dayNames = ['อาทิตย์', 'จันทร์', 'อังคาร', 'พุธ', 'พฤหัสบดี', 'ศุกร์', 'เสาร์'];
    const dayName = dayNames[now.getDay()]; // ดึงชื่อวัน

    const dateString = now.toLocaleString('th-TH', options);

    // ตรวจสอบช่วงเวลา
    let greeting;
    const hour = now.getHours();
    if (hour >= 5 && hour < 12) {
        greeting = "สวัสดีตอนเช้า 🌅☕";
    } else if (hour >= 12 && hour < 17) {
        greeting = "สวัสดีตอนบ่าย ☀️🍵";
    } else if (hour >= 17 && hour < 20) {
        greeting = "สวัสดีตอนเย็น 🌇🍷";
    } else {
        greeting = "สวัสดีตอนกลางคืน 🌙🌌";
    }

    // ใช้ innerHTML เพื่อเพิ่ม <br> สำหรับเว้นบรรทัด
    document.getElementById('clock').innerHTML = `${greeting}<br>วัน${dayName} ที่ ${dateString} น.`;
}

setInterval(updateTime, 1000); // อัปเดตทุก 1 วินาที
window.onload = updateTime; // โหลดเวลาทันทีเมื่อหน้าเปิด

// =====================================================================================================
// Alert ก่อนกดส่งแจ้งปัญหาการใช้งาน
document.addEventListener('DOMContentLoaded', function() {
    AlertProblem();
});

function AlertProblem() {
    const message = "โปรดตรวจสอบการกรอกข้อมูลการแจ้งปัญหาของท่านอย่างถี่ถ้วน<br>หากดำเนินการกดส่งข้อมูลแล้วจะไม่สามารถดำเนินการแก้ไขได้ <br><br>หากท่านมั่นใจในการตรวจสอบโปรดกดที่ปุ่ม <b>ตกลง</b> เพื่อส่งข้อมูลในการแจ้งปัญหาของท่าน";
    document.getElementById('modalMessage').innerHTML = message;
    $('#confirmationModal').modal('show');
    document.getElementById('confirmSubmit').addEventListener('click', function() {
        document.getElementById('reportForm').submit(); // ส่งฟอร์ม
    });
}

// =====================================================================================================
// เพิ่มการฟังเหตุการณ์คลิกปุ่ม "แสดงรูปภาพ"
document.addEventListener("DOMContentLoaded", function () {
    var imageButtons = document.querySelectorAll('[data-bs-target="#imageModal"]');
    imageButtons.forEach(function(button) {
        button.addEventListener('click', function() {
            var imageUrl = button.getAttribute('data-image');
            document.getElementById('modalImage').src = imageUrl;
        });
    });
});

// =====================================================================================================
// เรียกใช้เมื่อมีการค้นหาหรือเลือกสถานะ
function searchData() {
    applySearchAndFilter();
}

function filterStatus() {
    applySearchAndFilter();
}

// ฟังก์ชันรวม: ค้นหา + กรอง + รีเซ็ตลำดับ
function applySearchAndFilter() {
    const input = document.getElementById('searchInput').value.toLowerCase();
    const filterValue = document.getElementById('statusFilter').value;
    const rows = document.querySelectorAll('.problem-row');

    let count = 0;

    rows.forEach(row => {
        const name = row.cells[1].textContent.toLowerCase();           // ชื่อ
        const issueType = row.cells[2].textContent.toLowerCase();      // ประเภทปัญหา
        const description = row.cells[3].textContent.toLowerCase();   // รายละเอียด
        const statusText = row.querySelector('.status-badge').textContent.trim(); // สถานะ

        const matchSearch = name.includes(input) || issueType.includes(input) || description.includes(input);
        const matchStatus = filterValue === "" || statusText.includes(getStatusText(filterValue));

        if (matchSearch && matchStatus) {
            row.style.display = '';
            count++;
            row.cells[0].textContent = count; // รีเซ็ตลำดับ (column ลำดับอยู่ที่ cell[0])
        } else {
            row.style.display = 'none';
        }
    });

    document.getElementById('resultCount').textContent = `มีทั้งหมด ${count} รายการ`;
}

// ฟังก์ชันแปลงรหัสสถานะเป็นข้อความ
function getStatusText(statusCode) {
    switch (statusCode) {
        case '1': return 'ปัญหาที่รอการแก้ไข';
        case '2': return 'ปัญหาที่กำลังดำเนินการ';
        case '3': return 'ปัญหาที่แก้ไขเสร็จสิ้น';
        default: return '';
    }
}

// เมื่อโหลดหน้าจอครั้งแรก
window.onload = function () {
    applySearchAndFilter();
};

// =====================================================================================================
let currentPage = 1;
let rowsPerPage = 10;

let filteredRows = []; // เพื่อเก็บข้อมูลที่ผ่านการกรองแล้ว

function paginateTable() {
    // ใช้ filteredRows แทนการเลือกทั้งหมด
    const totalPages = Math.ceil(filteredRows.length / rowsPerPage);

    // ซ่อนแถวทั้งหมดก่อน
    const rows = document.querySelectorAll('.problem-row');
    rows.forEach(row => row.style.display = 'none');

    const start = (currentPage - 1) * rowsPerPage;
    const end = start + rowsPerPage;

    // แสดงแถวที่อยู่ในช่วงของหน้า
    filteredRows.slice(start, end).forEach((row, index) => {
        row.style.display = '';
        row.cells[0].textContent = start + index + 1; // อัปเดตลำดับ
    });

    document.getElementById('resultCount').textContent =
        `มีทั้งหมด ${filteredRows.length} รายการ - แสดงหน้า ${currentPage} จาก ${totalPages}`;

    renderPagination(totalPages);
}

function renderPagination(totalPages) {
    const pagination = document.getElementById('pagination');
    pagination.innerHTML = ''; // ล้างเนื้อหาภายใน

    // ปุ่มก่อนหน้า
    const prevBtn = document.createElement('button');
    prevBtn.textContent = 'ก่อนหน้า';
    prevBtn.className = 'btn btn-dark me-2';
    prevBtn.disabled = currentPage === 1;
    prevBtn.onclick = () => {
        if (currentPage > 1) {
            currentPage--;
            paginateTable();
        }
    };
    pagination.appendChild(prevBtn);

    // ปุ่มถัดไป
    const nextBtn = document.createElement('button');
    nextBtn.textContent = 'ถัดไป';
    nextBtn.className = 'btn btn-dark ms-2';
    nextBtn.disabled = currentPage === totalPages;
    nextBtn.onclick = () => {
        if (currentPage < totalPages) {
            currentPage++;
            paginateTable();
        }
    };
    pagination.appendChild(nextBtn);
}


function applySearchAndFilter() {
    const input = document.getElementById('searchInput').value.toLowerCase();
    const filterValue = document.getElementById('statusFilter').value;
    const rows = document.querySelectorAll('.problem-row');

    filteredRows = []; // ล้างข้อมูลที่กรองไว้ก่อน

    rows.forEach(row => {
        const name = row.cells[1].textContent.toLowerCase();
        const issueType = row.cells[2].textContent.toLowerCase();
        const description = row.cells[3].textContent.toLowerCase();
        const statusText = row.querySelector('.status-badge').textContent.trim();

        const matchSearch = name.includes(input) || issueType.includes(input) || description.includes(input);
        const matchStatus = filterValue === "" || statusText.includes(getStatusText(filterValue));

        if (matchSearch && matchStatus) {
            filteredRows.push(row); // เก็บแถวที่ตรงเงื่อนไข
        }
    });

    currentPage = 1; // รีเซ็ตหน้าเมื่อมีการกรอง/ค้นหา
    paginateTable(); // เรียก paginateTable เพื่อแสดงข้อมูลหลังการกรอง
}

function getStatusText(statusCode) {
    switch (statusCode) {
        case '1': return 'ปัญหาที่รอการแก้ไข';
        case '2': return 'ปัญหาที่กำลังดำเนินการ';
        case '3': return 'ปัญหาที่แก้ไขเสร็จสิ้น';
        default: return '';
    }
}

window.onload = function () {
    applySearchAndFilter(); // เรียกใช้ applySearchAndFilter เพื่อแสดงข้อมูลเมื่อโหลดหน้าแรก
};

// =====================================================================================================
function openContactInfo() {
    var myWindow = window.open("", "", "width=550,height=280");

    myWindow.document.write(`
    <!DOCTYPE html>
    <html lang="th">
    <head>
        <meta charset="UTF-8">
        <title>ติดต่อ / สอบถาม</title>
        <link href="https://fonts.googleapis.com/css2?family=Prompt&display=swap" rel="stylesheet">
        <style>
            body {
                font-family: 'Prompt', sans-serif;
                padding: 20px;
                line-height: 1.8;
                color: #333;
            }
            h2 {
                font-size: 22px;
                color: #222;
                margin-bottom: 20px;
            }
            p {
                font-size: 16px;
                margin: 10px 0;
            }
            strong {
                color: #007bff;
            }
        </style>
    </head>
    <body>
        <h2>ติดต่อสอบถามเพิ่มเติมได้ที่</h2>
        <p><strong>เบอร์โทร : </strong> 01-223-442 ต่อ 566</p>
        <p><strong>ไลน์ : </strong> ITsupportABC</p>
        <p><strong>อีเมล : </strong> example.abc@itabc.com</p>

        <script>
            // ป้องกันการรีเฟรชด้วย F5 หรือ Ctrl+R
            document.addEventListener('keydown', function(event) {
                if ((event.key === 'F5') || (event.ctrlKey && event.key === 'r')) {
                    event.preventDefault();
                    alert('ไม่สามารถรีเฟรชหน้าต่างนี้ได้');
                }
            });
        </script>
    </body>
    </html>
    `);
}

// =====================================================================================================
// ยกเลิกการแจ้งปัญหา
function confirmCancel(button) {
    const problemId = button.getAttribute('data-id');
    const modal = new bootstrap.Modal(document.getElementById('confirmCancelModal'));
    const confirmCancelBtn = document.getElementById('confirmCancelBtn');

    modal.show();

    confirmCancelBtn.onclick = function() {
        document.getElementById('cancelProblemId').value = problemId;
        document.getElementById('cancelForm').submit();  // ใช้ POST ไปที่ Controller
    };
}

    document.addEventListener("DOMContentLoaded", function () {
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
    tooltipTriggerList.map(function (tooltipTriggerEl) {
    return new bootstrap.Tooltip(tooltipTriggerEl)
})
});
// =====================================================================================================
// เก่า ไป ใหม่ ใหม่ ไป เก่า
let sortDirection = {}; // ใช้เก็บสถานะการ sort ของแต่ละ column

function sortTable(colIndex) {
    const table = document.getElementById("problemsTable");
    const rows = Array.from(table.querySelectorAll("tbody > tr"));
    const isDateColumn = colIndex === 6;
    const isNumberColumn = colIndex === 0;

    // Toggle direction
    sortDirection[colIndex] = !sortDirection[colIndex];

    rows.sort((a, b) => {
        let valA = a.children[colIndex].textContent.trim();
        let valB = b.children[colIndex].textContent.trim();

        if (isDateColumn) {
            // แปลงวันที่ (dd/MM/yyyy HH:mm) ให้เปรียบเทียบได้
            const parseDate = (str) => {
                const [d, m, yTime] = str.split("/");
                const [y, time] = yTime.split(" ");
                return new Date(`${y}-${m}-${d}T${time}`);
            };
            valA = parseDate(valA);
            valB = parseDate(valB);
        } else if (isNumberColumn) {
            valA = parseInt(valA);
            valB = parseInt(valB);
        }

        if (valA < valB) return sortDirection[colIndex] ? -1 : 1;
        if (valA > valB) return sortDirection[colIndex] ? 1 : -1;
        return 0;
    });

    // ล้าง tbody แล้วแสดงแถวใหม่ที่เรียงแล้ว
    const tbody = table.querySelector("tbody");
    tbody.innerHTML = "";
    rows.forEach(row => tbody.appendChild(row));
}
// =====================================================================================================
// showdata
function changeRowsPerPage() {
    const select = document.getElementById('rowsPerPageSelect');
    const selectedValue = parseInt(select.value);
    rowsPerPage = selectedValue;
    currentPage = 1;
    paginateTable();
}
// =====================================================================================================


