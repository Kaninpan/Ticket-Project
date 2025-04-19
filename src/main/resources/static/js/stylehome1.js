// =====================================================================================================
// Filter report user admin
document.addEventListener("DOMContentLoaded", function () {
    const table = document.getElementById("usersTable");
    const rows = Array.from(table.querySelectorAll("tbody tr"));
    const searchInput = document.getElementById("searchInput");
    const roleFilter = document.getElementById("roleFilter");
    const statusFilter = document.getElementById("statusFilter");
    const summaryText = document.getElementById("summaryText");
    const itemsPerPageSelect = document.getElementById("itemsPerPage");
    const paginationContainer = document.getElementById("paginationControls");

    let currentPage = 1;
    let itemsPerPage = parseInt(itemsPerPageSelect.value);
    let prevPageBtn, nextPageBtn;

    // สร้างปุ่ม pagination ด้วย JavaScript
    function createPaginationButtons() {
        paginationContainer.innerHTML = "";

        prevPageBtn = document.createElement("button");
        prevPageBtn.id = "prevPageBtn";
        prevPageBtn.className = "btn btn-secondary";
        prevPageBtn.textContent = "ก่อนหน้า";
        prevPageBtn.disabled = true;
        paginationContainer.appendChild(prevPageBtn);

        nextPageBtn = document.createElement("button");
        nextPageBtn.id = "nextPageBtn";
        nextPageBtn.className = "btn btn-secondary";
        nextPageBtn.textContent = "ถัดไป";
        paginationContainer.appendChild(nextPageBtn);

        // กดก่อนหน้า
        prevPageBtn.addEventListener("click", () => {
            if (currentPage > 1) {
                currentPage--;
                updateTable();
            }
        });

        // กดถัดไป
        nextPageBtn.addEventListener("click", () => {
            const filteredRows = filterRows();
            const totalPages = Math.ceil(filteredRows.length / itemsPerPage);
            if (currentPage < totalPages) {
                currentPage++;
                updateTable();
            }
        });
    }

    // ฟังก์ชันกรองข้อมูล
    function filterRows() {
        const searchTerm = searchInput.value.toLowerCase();
        const selectedRole = roleFilter.value.toLowerCase();
        const selectedStatus = statusFilter.value.toLowerCase();

        return rows.filter(row => {
            const columns = row.querySelectorAll("td");
            const name = columns[1].textContent.toLowerCase();
            const email = columns[2].textContent.toLowerCase();
            const phone = columns[3].textContent.toLowerCase();
            const username = columns[4].textContent.toLowerCase();
            const role = columns[5].textContent.toLowerCase();
            const status = columns[7].textContent.toLowerCase();

            const matchSearch = [name, email, phone, username].some(text =>
                text.includes(searchTerm)
            );

            const matchRole = selectedRole === "" || role.includes(selectedRole);
            const matchStatus = selectedStatus === "" ||
                (selectedStatus === "1" && status.includes("ใช้งาน")) ||
                (selectedStatus === "9" && status.includes("ปิดบัญชี"));

            return matchSearch && matchRole && matchStatus;
        });
    }

    // ฟังก์ชันอัปเดตตารางและปุ่ม
    function updateTable() {
        const filteredRows = filterRows();
        const totalItems = filteredRows.length;
        const totalPages = Math.ceil(totalItems / itemsPerPage);

        currentPage = Math.min(currentPage, totalPages || 1);
        const start = (currentPage - 1) * itemsPerPage;
        const end = start + itemsPerPage;

        rows.forEach(row => row.style.display = "none");
        filteredRows.slice(start, end).forEach(row => row.style.display = "");

        summaryText.textContent = `มีทั้งหมด ${totalItems} รายการ - แสดงหน้า ${currentPage} จาก ${totalPages || 1}`;

        prevPageBtn.disabled = currentPage === 1;
        nextPageBtn.disabled = currentPage === totalPages;
    }

    // Event Listener สำหรับ input และ filter
    searchInput.addEventListener("input", () => {
        currentPage = 1;
        updateTable();
    });

    roleFilter.addEventListener("change", () => {
        currentPage = 1;
        updateTable();
    });

    statusFilter.addEventListener("change", () => {
        currentPage = 1;
        updateTable();
    });

    itemsPerPageSelect.addEventListener("change", () => {
        itemsPerPage = parseInt(itemsPerPageSelect.value);
        currentPage = 1;
        updateTable();
    });

    // เรียกเมื่อโหลดหน้า
    createPaginationButtons();
    updateTable();
});


