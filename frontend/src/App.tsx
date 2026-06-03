import { FormEvent, useEffect, useState } from 'react';

type Todo = {
  id: number;
  title: string;
  completed: boolean;
};

const API_BASE = 'http://localhost:8080/api/todos';

function App() {
  const [todos, setTodos] = useState<Todo[]>([]);
  const [title, setTitle] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    fetchTodos();
  }, []);

  const fetchTodos = async () => {
    try {
      setLoading(true);
      const response = await fetch(API_BASE);
      const data = await response.json();
      setTodos(data);
    } catch (e) {
      setError('データの取得に失敗しました。');
    } finally {
      setLoading(false);
    }
  };

  const addTodo = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (!title.trim()) {
      return;
    }

    const newTodo = { title: title.trim(), completed: false };
    try {
      const response = await fetch(API_BASE, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newTodo),
      });
      const created = await response.json();
      setTodos([...todos, created]);
      setTitle('');
    } catch (e) {
      setError('TODOの追加に失敗しました。');
    }
  };

  const toggleCompleted = async (todo: Todo) => {
    try {
      const response = await fetch(`${API_BASE}/${todo.id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ ...todo, completed: !todo.completed }),
      });
      const updated = await response.json();
      setTodos(todos.map((item) => (item.id === updated.id ? updated : item)));
    } catch (e) {
      setError('完了状態の更新に失敗しました。');
    }
  };

  const deleteTodo = async (id: number) => {
    try {
      await fetch(`${API_BASE}/${id}`, {
        method: 'DELETE',
      });
      setTodos(todos.filter((todo) => todo.id !== id));
    } catch (e) {
      setError('TODOの削除に失敗しました。');
    }
  };

  return (
    <div className="min-h-screen bg-slate-100 py-10 px-4">
      <div className="mx-auto max-w-2xl rounded-3xl bg-white p-8 shadow-lg shadow-slate-200">
        <h1 className="mb-4 text-center text-3xl font-bold text-slate-900">TODOアプリ</h1>

        <form onSubmit={addTodo} className="mb-6 flex gap-2">
          <input
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            placeholder="TODOを入力してください"
            className="w-full rounded-xl border border-slate-300 px-4 py-3 text-slate-900 outline-none focus:border-sky-500"
          />
          <button
            type="submit"
            className="rounded-xl bg-sky-600 px-5 py-3 text-white transition hover:bg-sky-700"
          >
            追加
          </button>
        </form>

        {error && <div className="mb-4 rounded-xl bg-rose-100 px-4 py-3 text-rose-700">{error}</div>}

        {loading ? (
          <div className="text-center text-slate-500">読み込み中...</div>
        ) : todos.length === 0 ? (
          <div className="text-center text-slate-500">TODOがありません。</div>
        ) : (
          <ul className="space-y-3">
            {todos.map((todo) => (
              <li key={todo.id} className="flex items-center justify-between rounded-2xl border border-slate-200 bg-slate-50 p-4">
                <label className="flex items-center gap-3">
                  <input
                    type="checkbox"
                    checked={todo.completed}
                    onChange={() => toggleCompleted(todo)}
                    className="h-5 w-5 rounded border-slate-300 text-sky-600"
                  />
                  <span className={todo.completed ? 'text-slate-400 line-through' : 'text-slate-900'}>
                    {todo.title}
                  </span>
                </label>
                <button
                  onClick={() => deleteTodo(todo.id)}
                  className="rounded-xl bg-rose-500 px-3 py-2 text-sm text-white transition hover:bg-rose-600"
                >
                  削除
                </button>
              </li>
            ))}
          </ul>
        )}
      </div>
    </div>
  );
}

export default App;
