import { reactive, watch } from 'vue';

const stored = JSON.parse(localStorage.getItem('notes')) || [];
const notes = reactive(stored);

const addNote = (note) => {
  notes.push({ ...note, id: Date.now() });
};

const updateNote = (id, updatedNote) => {
  const index = notes.findIndex((n) => n.id === id);
  if (index !== -1) notes[index] = { ...notes[index], ...updatedNote };
};

const deleteNote = (id) => {
  const index = notes.findIndex((n) => n.id === id);
  if (index !== -1) notes.splice(index, 1);
};

watch(notes, (val) => {
  localStorage.setItem('notes', JSON.stringify(val));
}, { deep: true });

export function useNotes() {
  return { notes, addNote, updateNote, deleteNote };
}