import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itu.lsm.R
import com.itu.lsm.classes.Task

class TaskBigAdapter(private var tasks: List<Task>,
                     private val onCancelClick: (Task) -> Unit,
                     private val onContactClick: (Task) -> Unit) : RecyclerView.Adapter<TaskBigAdapter.TaskViewHolder>() {


    // Interface for click events
    interface OnTaskClickListener {
        fun onCardClicked(task: Task)
    }

    var taskClickListener: OnTaskClickListener? = null

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvLocation: TextView = itemView.findViewById(R.id.tvLocation)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        val buttonCancel: Button = itemView.findViewById(R.id.buttonCancel)
        val buttonContact: Button = itemView.findViewById(R.id.buttonContact)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskBigAdapter.TaskViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_task_card_big, parent, false)
        return TaskViewHolder(v)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.tvTitle.text = task.title
        holder.tvLocation.text = task.loc
        holder.tvTime.text = task.date + " " + task.time
        holder.imageView.setImageResource(getImageResourceBasedOnTitle(task.title))

        holder.buttonCancel.setOnClickListener { onCancelClick(task) }
        holder.buttonContact.setOnClickListener { onContactClick(task) }

        holder.itemView.setOnClickListener {
            // Make sure the click is not on the buttons
            if (!holder.buttonCancel.isPressed && !holder.buttonContact.isPressed) {
                taskClickListener?.onCardClicked(task)
            }
        }
    }

    private fun getImageResourceBasedOnTitle(title: String): Int {
        return when (title) {
            "Tutoring" -> R.drawable.ai_tutoring
            "Cleaning" -> R.drawable.ai_cleaning
            "Counselling" -> R.drawable.ai_counselling
            "Training" -> R.drawable.ai_training
            "Cooking lesson" -> R.drawable.ai_cooking
            "English lesson" -> R.drawable.ai_english
            else -> R.drawable.logo
        }
    }

    override fun getItemCount() = tasks.size

    fun updateTasks(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }
}
