// / ฟังก์ชันเปิด/ปิด Sidebar
document.addEventListener('DOMContentLoaded', function () {
    document.querySelector('.toggle-sidebar-btn').addEventListener('click', function() {
        document.querySelector('.sidebar').classList.toggle('show');
    });
});
// =========================================================================================
// Alert Edit Profile success
setTimeout(function() {
    var alertElement = document.getElementById('successAlert');
    if (alertElement) {
        alertElement.classList.remove('show');
        alertElement.classList.add('fade');
        setTimeout(function() {
            alertElement.style.display = 'none';
        }, 150); // เวลาที่ใช้ในการทำให้จางลง
    }
}, 2300);

// =========================================================================================

// Alert Exit
document.addEventListener("DOMContentLoaded", function() {
    document.getElementById('logoutButton').onclick = function() {
        document.getElementById('logoutAlert').classList.remove('d-none');
    };

    document.getElementById('cancelButton').onclick = function() {
        document.getElementById('logoutAlert').classList.add('d-none');
    };

    document.getElementById('confirmButton').onclick = function() {
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
const modalMessage = "คุณต้องการที่จะแก้ไขข้อมูลส่วนตัว <br> โดยที่ไม่ต้องการแก้ไขรหัสผ่านใช่หรือไม่ ? <br><br> หากแก้ไขทั้ง 2 ส่วนสามารถกดที่ปุ่ม <b>ตกลง</b>";

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


