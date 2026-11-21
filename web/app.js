let organizaciones = [];
let orgActualId = null;

function abrirModal(id) {
  document.getElementById(id).classList.add('active');
}

function cerrarModal(id) {
  document.getElementById(id).classList.remove('active');
}

function crearOrg() {
  const nombre = document.getElementById('inputOrg').value.trim();
  if (!nombre) return;

  const nueva = { id: Date.now(), nombre, columnas: [], tareas: [] };
  organizaciones.push(nueva);
  orgActualId = nueva.id;

  document.getElementById('inputOrg').value = '';
  cerrarModal('modalOrg');
  actualizarSelectOrg();
  renderizar();
}

function actualizarSelectOrg() {
  const select = document.getElementById('selectOrg');
  select.innerHTML = organizaciones.map(o =>
    `<option value="${o.id}" ${o.id === orgActualId ? 'selected' : ''}>${o.nombre}</option>`
  ).join('');
}

function cambiarOrg() {
  orgActualId = Number(document.getElementById('selectOrg').value);
  renderizar();
}

function getOrgActual() {
  return organizaciones.find(o => o.id === orgActualId);
}

function crearColumna() {
  const org = getOrgActual();
  const nombre = document.getElementById('inputCol').value.trim();
  if (!org || !nombre) return;

  org.columnas.push({ id: Date.now(), nombre });
  document.getElementById('inputCol').value = '';
  cerrarModal('modalCol');
  renderizar();
}

function crearTarea() {
  const org = getOrgActual();
  const titulo = document.getElementById('inputTitulo').value.trim();
  const responsable = document.getElementById('inputResp').value.trim();
  const prioridad = document.getElementById('inputPrioridad').value;
  if (!org || !titulo || org.columnas.length === 0) return;

  org.tareas.push({
    id: Date.now(),
    titulo,
    responsable: responsable || 'Sin asignar',
    prioridad,
    columnaId: org.columnas[0].id
  });

  document.getElementById('inputTitulo').value = '';
  document.getElementById('inputResp').value = '';
  cerrarModal('modalTarea');
  renderizar();
}

function moverTarea(tareaId, nuevaColId) {
  const org = getOrgActual();
  const tarea = org.tareas.find(t => t.id === tareaId);
  if (tarea) {
    tarea.columnaId = Number(nuevaColId);
    renderizar();
  }
}

function renderizar() {
  const workspace = document.getElementById('workspace');
  const org = getOrgActual();

  document.getElementById('btnCol').disabled = !org;
  document.getElementById('btnTarea').disabled = !org || org.columnas.length === 0;

  if (!org) return;

  if (org.columnas.length === 0) {
    workspace.innerHTML = `
      <div class="empty-box">
        <h3>Organización: ${org.nombre}</h3>
        <p>Aún no hay estados en el flujo. Haz clic en <strong>+ Estado</strong> para crear tu primera columna.</p>
      </div>`;
    return;
  }

  workspace.innerHTML = `
    <h3>Tablero de ${org.nombre}</h3>
    <div class="board">
      ${org.columnas.map(col => {
        const tareasCol = org.tareas.filter(t => t.columnaId === col.id);
        return `
          <div class="column">
            <h3>${col.nombre} (${tareasCol.length})</h3>
            ${tareasCol.map(t => `
              <div class="card">
                <strong>${t.titulo}</strong>
                <div class="card-meta">Prioridad: ${t.prioridad} | Resp: ${t.responsable}</div>
                <select style="margin-top:8px; width:100%" onchange="moverTarea(${t.id}, this.value)">
                  ${org.columnas.map(c =>
                    `<option value="${c.id}" ${c.id === col.id ? 'selected' : ''}>Mover a: ${c.nombre}</option>`
                  ).join('')}
                </select>
              </div>
            `).join('')}
          </div>`;
      }).join('')}
    </div>`;
}